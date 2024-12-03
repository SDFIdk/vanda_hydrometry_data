package dk.dataforsyningen.vanda_hydrometry_data.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesExaminationTypeResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesMeasurementResultResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;
import java.io.IOException;
import java.net.URI;
import java.security.InvalidParameterException;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.apache.logging.log4j.util.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Service class providing API access to DMP's VandaH.
 *
 * @author Radu Dudici
 */
@Service
public class VandahDmpApiService {

  private static final Logger logger = LoggerFactory.getLogger(VandahDmpApiService.class);

  @Autowired
  RestClient restClient;

  public DmpHydroApiResponsesStationResponse[] getStations(
      String vandahApiUrl,
      String stationId,
      String operatorStationId,
      Integer parameterSc,
      Integer examinationTypeSc,
      OffsetDateTime withResultsAfter,
      OffsetDateTime withResultsCreatedAfter
  ) {

    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(vandahApiUrl)
        .queryParamIfPresent("stationId", Optional.ofNullable(stationId))
        .queryParamIfPresent("operatorStationId", Optional.ofNullable(operatorStationId))
        .queryParamIfPresent("parameterSc", Optional.ofNullable(parameterSc))
        .queryParamIfPresent("examinationTypeSc", Optional.ofNullable(examinationTypeSc))
        .queryParamIfPresent("withResultsAfter", Optional.ofNullable(withResultsAfter))
        .queryParamIfPresent("withResultsCreatedAfter",
            Optional.ofNullable(withResultsCreatedAfter));

    URI uri = uriBuilder.build().toUri();

    logger.info("Call: " + uri);

    DmpHydroApiResponsesStationResponse[] stations = restClient.get().uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(status -> status.value() >= 400, (request, response) -> {
          logger.error("Error retrieving stations: [" + response.getStatusCode() + "] " +
              response.getStatusText());
          String message = errorMessageFromJson(response);
          throw new InternalException("Error retrieving stations: " + message);
        })
        .body(DmpHydroApiResponsesStationResponse[].class);

    return stations;
  }

  public DmpHydroApiResponsesExaminationTypeResponse[] getExaminationTypes(String vandahApiUrl) {

    logger.info("Call: " + vandahApiUrl);

    DmpHydroApiResponsesExaminationTypeResponse[] types = restClient.get().uri(vandahApiUrl)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(status -> status.value() >= 400, (request, response) -> {
          logger.error("Error retrieving examination types: [" + response.getStatusCode() + "] " +
              response.getStatusText());
          String message = errorMessageFromJson(response);
          throw new InternalException("Error retrieving examination types: " + message);
        })
        .body(DmpHydroApiResponsesExaminationTypeResponse[].class);

    return types;
  }


  /**
   * Call the API for the given endpoint to retrieve measurements
   *
   * @param vandahApiUrl           the URL base
   * @param stationId              should be set to actual station Id
   * @param operatorStationId
   * @param measurementPointNumber
   * @param from
   * @param to
   * @param createdAfter
   * @return
   */
  public DmpHydroApiResponsesMeasurementResultResponse[] getMeasurementsForStation(
      String vandahApiUrl,
      String stationId,
      String operatorStationId,
      Integer measurementPointNumber,
      OffsetDateTime from,
      OffsetDateTime to,
      OffsetDateTime createdAfter
  ) {

    if (isEmpty(stationId) && isEmpty(operatorStationId)) {
      throw new InvalidParameterException("Station id or Operator station id must be specified.");
    }

    UriComponentsBuilder uriBuilder = UriComponentsBuilder
        .fromUriString(vandahApiUrl)
        .queryParamIfPresent("stationId", Optional.of(stationId))
        .queryParamIfPresent("operatorStationId", Optional.ofNullable(operatorStationId))
        .queryParamIfPresent("measurementPointNumber", Optional.ofNullable(measurementPointNumber))
        .queryParamIfPresent("from", Optional.ofNullable(from))
        .queryParamIfPresent("to", Optional.ofNullable(to))
        .queryParamIfPresent("createdAfter", Optional.ofNullable(createdAfter));

    URI uri = uriBuilder.build().toUri();

    DmpHydroApiResponsesMeasurementResultResponse[] results = null;

    // Max try two times
    int running = 2;
    while (running > 0) {
      try {
        logger.info("Call: " + uri);

        results = restClient.get().uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(status -> status.value() >= 400, (request, response) -> {
              logger.error("Error response from " + uri + ": [" + response.getStatusCode() + "] " +
                  response.getStatusText());
              String message = errorMessageFromJson(response);
              throw new InternalException("Error retrieving data: " + message);
            })
            .body(DmpHydroApiResponsesMeasurementResultResponse[].class);
        // Stop the while loop
        running = 0;
      } catch (RestClientException | InternalException exception) {
        logger.warn("Exception received: " + exception + ". Try again..." + exception.getMessage());
        running--;
        if (running > 0) {
          try {
            Thread.sleep(5000);
          } catch (InterruptedException exception1) {
            logger.warn("Exception received during sleep: " + exception1 + ". Error message is: " +
                exception1.getMessage());
          }
        }
      }
    }

    return results;
  }

  /**
   * Returns the error message for the given key from the json body from the ClientHttpResponse
   *
   * @param response ClientHttpResponse
   * @return value as string
   */
  private String errorMessageFromJson(ClientHttpResponse response) {
    String value = "";
    try {
      JsonNode rootNode = new ObjectMapper().readTree(response.getBody());
      // error message from the API belongs to the key "message"
      JsonNode node = rootNode.get("message");
      if (node != null) {
        value = node.asText();
      }
    } catch (IOException exception) {
      logger.error("Exception received trying reading error message from API: " + exception +
          ". Exception message is: " + exception.getMessage());
    }
    return value;
  }

  private boolean isEmpty(String validate) {
    return validate == null || validate.isBlank();
  }
}
