package dk.dataforsyningen.vanda_hydrometry_data.service;

import java.net.URI;
import java.security.InvalidParameterException;
import java.time.OffsetDateTime;

import org.apache.logging.log4j.util.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesExaminationTypeResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesMeasurementResultResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;

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
	
	@Autowired
	VandaHydrometryDataConfig config;
	
	public DmpHydroApiResponsesStationResponse[] getStations(
			String vandahApiUrl,
			String stationId, 
			String operatorStationId,
			String stationOwnerCvr,
			String operatorCvr,
			Integer parameterSc,
			Integer examinationTypeSc,
			OffsetDateTime withResultsAfter,
			OffsetDateTime withResultsCreatedAfter,
			String format
			) {
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(vandahApiUrl);
		if (!isEmpty(stationId)) { 
			uriBuilder.queryParam("stationId", stationId); 
		}
		if (!isEmpty(operatorStationId)) { 
			uriBuilder.queryParam("operatorStationId", operatorStationId); 
		}
		if (!isEmpty(stationOwnerCvr)) { 
			uriBuilder.queryParam("stationOwnerCvr", stationOwnerCvr); 
		}
		if (!isEmpty(operatorCvr)) { 
			uriBuilder.queryParam("operatorCvr", operatorCvr); 
		}
		if (!isEmpty(parameterSc)) { 
			uriBuilder.queryParam("parameterSc", parameterSc); 
		}
		if (!isEmpty(examinationTypeSc)) { 
			uriBuilder.queryParam("examinationTypeSc", examinationTypeSc); 
		}
		if (!isEmpty(withResultsAfter)) { 
			uriBuilder.queryParam("withResultsAfter", withResultsAfter.toString()); 
		}
		if (!isEmpty(withResultsCreatedAfter)) { 
			uriBuilder.queryParam("withResultsCreatedAfter", withResultsCreatedAfter.toString()); 
		}
		if (!isEmpty(format)) { 
			uriBuilder.queryParam("format", format); 
		}
		
        URI uri = uriBuilder.build().toUri();
				
		logger.info("Call: " + uri.toString());
		
		DmpHydroApiResponsesStationResponse[] stations = restClient.get().uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					logger.error("Error retrieving stations: [" + response.getStatusCode() + "] " + response.getStatusText());
					String message = VandaHUtility.valueFromJson(response, "message");
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
					logger.error("Error retrieving examination types: [" + response.getStatusCode() + "] " + response.getStatusText());
					String message = VandaHUtility.valueFromJson(response, "message");
					throw new InternalException("Error retrieving examination types: " + message);
				})
				.body(DmpHydroApiResponsesExaminationTypeResponse[].class);
				
		return types;
		
	}
		
	
	/**
	 * Call the API for the given endpoint to retrieve measurements
	 * 
	 * @param vandahApiUrl the URL base
	 * @param stationId should be set to actual station Id
	 * @param operatorStationId
	 * @param measurementPointNumber
	 * @param from
	 * @param to
	 * @param createdAfter
	 * @param format
	 * @return
	 */
	public DmpHydroApiResponsesMeasurementResultResponse[] getMeasurementsForStation(
			String vandahApiUrl,
			String stationId, 
			String operatorStationId,
			Integer measurementPointNumber,
			OffsetDateTime from,
			OffsetDateTime to,
			OffsetDateTime createdAfter,
			String format) {
		
		if (isEmpty(stationId) && isEmpty(operatorStationId)) {
			throw new InvalidParameterException("Station id or Operator station id must be specified.");
		}
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(vandahApiUrl);
		if (!isEmpty(stationId)) { 
			uriBuilder.queryParam("stationId", stationId); 
		}
		if (!isEmpty(operatorStationId)) { 
			uriBuilder.queryParam("operatorStationId", operatorStationId); 
		}
		if (!isEmpty(measurementPointNumber)) { 
			uriBuilder.queryParam("measurementPointNumber", measurementPointNumber); 
		}
		if (!isEmpty(from)) { 
			uriBuilder.queryParam("from", from.toString()); 
		}
		if (!isEmpty(to)) { 
			uriBuilder.queryParam("to", to.toString()); 
		}
		if (!isEmpty(createdAfter)) { 
			uriBuilder.queryParam("createdAfter", createdAfter.toString()); 
		}
		if (!isEmpty(format)) { 
			uriBuilder.queryParam("format", format); 
		}
		
        URI uri = uriBuilder.build().toUri();

		DmpHydroApiResponsesMeasurementResultResponse[] results = null;

		// Max try two times
		int running = 2;
		while (running > 0) {
			try {
				logger.info("Call: " + uri.toString());

				results = restClient.get().uri(uri)
						.accept(MediaType.APPLICATION_JSON)
						.retrieve()
						.onStatus(status -> status.value() >= 400, (request, response) -> {
							logger.error("Error response from " + uri.toString() + ": [" + response.getStatusCode() + "] " + response.getStatusText());
							String message = VandaHUtility.valueFromJson(response, "message");
							throw new InternalException("Error retrieving data: " + message);
						})
						.body(DmpHydroApiResponsesMeasurementResultResponse[].class);
				// Stop the while loop
				running = 0;
			} catch (ResourceAccessException | InternalException exception) {
				logger.warn("Exception received. Try again..." + exception.getMessage());
				running--;
				if (running > 0) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException exception1) {
						// do nothing
					}
				}
			}
		}

		return results;
	}

	
	
	private boolean isEmpty(Object obj) {
		return obj == null || (obj instanceof String && ((String) obj).isEmpty());
	}

}
