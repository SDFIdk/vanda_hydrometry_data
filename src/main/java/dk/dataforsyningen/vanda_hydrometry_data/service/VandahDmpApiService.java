package dk.dataforsyningen.vanda_hydrometry_data.service;

import java.security.InvalidParameterException;
import java.time.OffsetDateTime;

import org.apache.logging.log4j.util.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

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
	
	private static final Logger log = LoggerFactory.getLogger(VandahDmpApiService.class);
	
	private final String STATIONS_PATH = "stations";
	private final String EXAMINATION_TYPES = "config/examination-types";
	private final String WATER_FLOWS = "water-flows";
	private final String WATER_LEVELS = "water-levels";
	
	@Autowired
	RestClient restClient;
	
	@Autowired
	VandaHydrometryDataConfig config;
	
	
	public DmpHydroApiResponsesStationResponse[] getAllStations() {

		String vandahApiUrl = config.getVandahDmpApiUrl() + STATIONS_PATH;
		
		VandaHUtility.logAndPrint(log, Level.INFO, config.isVerbose(), "Call: " + vandahApiUrl); 
				
		DmpHydroApiResponsesStationResponse[] stations = restClient.get().uri(vandahApiUrl)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					VandaHUtility.logAndPrint(log, Level.ERROR, false, "Error retrieving stations: [" + response.getStatusCode() + "] " + response.getStatusText());
					String message = VandaHUtility.valueFromJson(response, "message");
					throw new InternalException("Error retrieving stations: " + message);
				})
				.body(DmpHydroApiResponsesStationResponse[].class);
				
		return stations;
	}
	
	public DmpHydroApiResponsesStationResponse[] getStations(
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
		
		String del = "";
		StringBuilder vandahApiUrl = new StringBuilder(config.getVandahDmpApiUrl() + STATIONS_PATH + "?");
		if (!isEmpty(stationId)) { vandahApiUrl.append(del + "stationId=" + stationId ); del = "&"; }
		if (!isEmpty(operatorStationId)) { vandahApiUrl.append(del + "operatorStationId=" + operatorStationId); del = "&"; }
		if (!isEmpty(stationOwnerCvr)) { vandahApiUrl.append(del + "stationOwnerCvr=" + stationOwnerCvr); del = "&"; }
		if (!isEmpty(operatorCvr)) { vandahApiUrl.append(del + "operatorCvr=" + operatorCvr); del = "&"; }
		if (!isEmpty(parameterSc)) { vandahApiUrl.append(del + "parameterSc=" + parameterSc); del = "&"; }
		if (!isEmpty(examinationTypeSc)) { vandahApiUrl.append(del + "examinationTypeSc=" + examinationTypeSc); del = "&"; }
		if (!isEmpty(withResultsAfter)) { vandahApiUrl.append(del + "withResultsAfter=" + withResultsAfter); del = "&"; }
		if (!isEmpty(withResultsCreatedAfter)) { vandahApiUrl.append(del + "withResultsCreatedAfter=" + withResultsCreatedAfter); del = "&"; }
		if (!isEmpty(format)) { vandahApiUrl.append(del + "format=" + format); del = "&"; }
		
		VandaHUtility.logAndPrint(log, Level.INFO, config.isVerbose(), "Call: " + vandahApiUrl.toString());
		
		DmpHydroApiResponsesStationResponse[] stations = restClient.get().uri(vandahApiUrl.toString())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					VandaHUtility.logAndPrint(log, Level.ERROR, false, "Error retrieving stations: [" + response.getStatusCode() + "] " + response.getStatusText());
					String message = VandaHUtility.valueFromJson(response, "message");
					throw new InternalException("Error retrieving stations: " + message);
				})
				.body(DmpHydroApiResponsesStationResponse[].class);
				
		return stations;
	}
	
	public DmpHydroApiResponsesExaminationTypeResponse[] getExaminationTypes() {
		String vandahApiUrl = config.getVandahDmpApiUrl() + EXAMINATION_TYPES;
		
		VandaHUtility.logAndPrint(log, Level.INFO, config.isVerbose(), "Call: " + vandahApiUrl);
		
		DmpHydroApiResponsesExaminationTypeResponse[] types = restClient.get().uri(vandahApiUrl)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					VandaHUtility.logAndPrint(log, Level.ERROR, false, "Error retrieving examination types: [" + response.getStatusCode() + "] " + response.getStatusText());
					String message = VandaHUtility.valueFromJson(response, "message");
					throw new InternalException("Error retrieving examination types: " + message);
				})
				.body(DmpHydroApiResponsesExaminationTypeResponse[].class);
				
		return types;
		
	}
	
	
	/**
	 * Retrieves water levels.
	 * 
	 * @param stationId 
	 * @param operatorStationId
	 * @param measurementPointNumber
	 * @param from
	 * @param to
	 * @param createdAfter
	 * @param format
	 * @return array of results
	 */
	public DmpHydroApiResponsesMeasurementResultResponse[] getWaterLevels(
			String stationId, 
			String operatorStationId,
			Integer measurementPointNumber,
			OffsetDateTime from,
			OffsetDateTime to,
			OffsetDateTime createdAfter,
			String format) {
		
		return getWaterMeasurementsForStation(WATER_LEVELS, stationId, operatorStationId, measurementPointNumber, from, to, createdAfter, format);
	}
	
	
	/**
	 * Retrieves water flows.
	 * 
	 * @param stationId 
	 * @param operatorStationId
	 * @param measurementPointNumber
	 * @param from
	 * @param to
	 * @param createdAfter
	 * @param format
	 * @return array of results
	 */
	public DmpHydroApiResponsesMeasurementResultResponse[] getWaterFlows(
			String stationId, 
			String operatorStationId,
			Integer measurementPointNumber,
			OffsetDateTime from,
			OffsetDateTime to,
			OffsetDateTime createdAfter,
			String format) {
		
		return getWaterMeasurementsForStation(WATER_FLOWS, stationId, operatorStationId, measurementPointNumber, from, to, createdAfter, format);
	}
	
	
	/**
	 * Call the API for both WATER_LEVELS and WATER_FLOWS
	 * 
	 * @param stationId should be set to actual station Id
	 * @param operatorStationId
	 * @param measurementPointNumber
	 * @param from
	 * @param to
	 * @param createdAfter
	 * @param format
	 * @return
	 */
	private DmpHydroApiResponsesMeasurementResultResponse[] getWaterMeasurementsForStation(
			String endpoint,
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
		
		String del = "";
		StringBuilder vandahApiUrl = new StringBuilder(config.getVandahDmpApiUrl() + endpoint + "?");
		if (!isEmpty(stationId)) { vandahApiUrl.append(del + "stationId=" + stationId); del = "&"; }
		if (!isEmpty(operatorStationId)) { vandahApiUrl.append(del + "operatorStationId=" + operatorStationId); del = "&"; }
		if (!isEmpty(measurementPointNumber)) { vandahApiUrl.append(del + "measurementPointNumber=" + measurementPointNumber); del = "&"; }
		if (!isEmpty(from)) { vandahApiUrl.append(del + "from=" + from); del = "&"; }
		if (!isEmpty(to)) { vandahApiUrl.append(del + "to=" + to); del = "&"; }
		if (!isEmpty(createdAfter)) { vandahApiUrl.append(del + "createdAfter=" + createdAfter); del = "&"; }
		if (!isEmpty(format)) { vandahApiUrl.append(del + "format=" + format); del = "&"; }

		DmpHydroApiResponsesMeasurementResultResponse[] results = null;

		// Max try two times
		int running = 2;
		while (running > 0) {
			try {
				VandaHUtility.logAndPrint(log, Level.INFO, config.isVerbose(), "Call: " + vandahApiUrl);

				results = restClient.get().uri(vandahApiUrl.toString())
						.accept(MediaType.APPLICATION_JSON)
						.retrieve()
						.onStatus(status -> status.value() >= 400, (request, response) -> {
							VandaHUtility.logAndPrint(log, Level.ERROR, false, "Error retrieving " + endpoint + ": [" + response.getStatusCode() + "] " + response.getStatusText());
							String message = VandaHUtility.valueFromJson(response, "message");
							throw new InternalException("Error retrieving " + endpoint + ": " + message);
						})
						.body(DmpHydroApiResponsesMeasurementResultResponse[].class);
				// Stop the while loop
				running = 0;
			} catch (ResourceAccessException | InternalException exception) {
				VandaHUtility.logAndPrint(log, Level.WARN, false, "Exception received. Try again." + exception.getMessage());
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
