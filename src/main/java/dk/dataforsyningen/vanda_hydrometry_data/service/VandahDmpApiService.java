package dk.dataforsyningen.vanda_hydrometry_data.service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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
				
		DmpHydroApiResponsesStationResponse[] stations = restClient.get().uri(vandahApiUrl)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					VandaHUtility.logAndPrint(log, Level.ERROR, true, "Error retrieving stations: [" + response.getStatusCode() + "] " + response.getStatusText());
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
			Integer[] examinationTypeScArray,
			LocalDateTime withResultsAfter,
			LocalDateTime withResultsCreatedAfter,
			String format
			) {
		
		ArrayList<DmpHydroApiResponsesStationResponse[]> arrayResults = new ArrayList<>();
		for(Integer examinationTypeSc : examinationTypeScArray) {
			arrayResults.add(getStations(
					stationId, 
					operatorStationId,
					stationOwnerCvr,
					operatorCvr,
					parameterSc,
					examinationTypeSc,
					withResultsAfter,
					withResultsCreatedAfter,
					format
					));
		}
		
		DmpHydroApiResponsesStationResponse[] mergedResults = arrayResults.stream().
				flatMap(Arrays::stream).
				distinct().toArray(DmpHydroApiResponsesStationResponse[]::new);
		
		
		return mergedResults;
	}
	
	public DmpHydroApiResponsesStationResponse[] getStations(
			String stationId, 
			String operatorStationId,
			String stationOwnerCvr,
			String operatorCvr,
			Integer parameterSc,
			Integer examinationTypeSc,
			LocalDateTime withResultsAfter,
			LocalDateTime withResultsCreatedAfter,
			String format
			) {
		
		StringBuilder vandahApiUrl = new StringBuilder(config.getVandahDmpApiUrl() + STATIONS_PATH + "?")
				.append( !isEmpty(stationId) ? "stationId=" + stationId : "")
				.append( !isEmpty(operatorStationId) ? "operatorStationId=" + operatorStationId : "")
				.append( !isEmpty(stationOwnerCvr) ? "stationOwnerCvr=" + stationOwnerCvr : "")
				.append( !isEmpty(operatorCvr) ? "operatorCvr=" + operatorCvr : "")
				.append( !isEmpty(parameterSc) ? "parameterSc=" + parameterSc : "")
				.append( !isEmpty(examinationTypeSc) ? "createdAfter=" + examinationTypeSc : "")
				.append( !isEmpty(withResultsAfter) ? "withResultsAfter=" + withResultsAfter : "")
				.append( !isEmpty(withResultsCreatedAfter) ? "withResultsCreatedAfter=" + withResultsCreatedAfter : "")
				.append( !isEmpty(format) ? "format=" + format : "");
		
		log.info("Call: " + vandahApiUrl.toString());
		
		DmpHydroApiResponsesStationResponse[] stations = restClient.get().uri(vandahApiUrl.toString())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					VandaHUtility.logAndPrint(log, Level.ERROR, true, "Error retrieving stations: [" + response.getStatusCode() + "] " + response.getStatusText());
				})
				.body(DmpHydroApiResponsesStationResponse[].class);
				
		return stations;
	}
	
	public DmpHydroApiResponsesExaminationTypeResponse[] getExaminationTypes() {
		String vandahApiUrl = config.getVandahDmpApiUrl() + EXAMINATION_TYPES;
		
		log.info("Call: " + vandahApiUrl);
		
		DmpHydroApiResponsesExaminationTypeResponse[] types = restClient.get().uri(vandahApiUrl)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					VandaHUtility.logAndPrint(log, Level.ERROR, true, "Error retrieving examination types: [" + response.getStatusCode() + "] " + response.getStatusText());
				})
				.body(DmpHydroApiResponsesExaminationTypeResponse[].class);
				
		return types;
		
	}
	
	public DmpHydroApiResponsesMeasurementResultResponse[] getWaterLevels(
			String stationId, 
			String operatorStationId,
			int measurementPointNumber,
			LocalDateTime from,
			LocalDateTime to,
			LocalDateTime createdAfter,
			String format
			) {
		
		if (isEmpty(stationId) && isEmpty(operatorStationId)) {
			throw new InvalidParameterException("Station id or Operator station id must be specified.");
		}
		
		StringBuilder vandahApiUrl = new StringBuilder(config.getVandahDmpApiUrl() + WATER_LEVELS + "?")
							.append( !isEmpty(stationId) ? "stationId=" + stationId : "")
							.append( !isEmpty(operatorStationId) ? "operatorStationId=" + operatorStationId : "")
							.append( !isEmpty(measurementPointNumber) ? "measurementPointNumber=" + measurementPointNumber : "")
							.append( !isEmpty(from) ? "from=" + from : "")
							.append( !isEmpty(to) ? "to=" + to : "")
							.append( !isEmpty(createdAfter) ? "createdAfter=" + createdAfter : "")
							.append( !isEmpty(format) ? "format=" + format : "");
		
		log.info("Call: " + vandahApiUrl.toString());
		
		DmpHydroApiResponsesMeasurementResultResponse[] results = restClient.get().uri(vandahApiUrl.toString())
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.onStatus(status -> status.value() >= 400, (request, response) -> {
			VandaHUtility.logAndPrint(log, Level.ERROR, true, "Error retrieving water levels: [" + response.getStatusCode() + "] " + response.getStatusText());
		})
		.body(DmpHydroApiResponsesMeasurementResultResponse[].class);
		
		return results;
	}
	
	public DmpHydroApiResponsesMeasurementResultResponse[] getWaterFlows(
			String stationId, 
			String operatorStationId,
			int measurementPointNumber,
			LocalDateTime from,
			LocalDateTime to,
			LocalDateTime createdAfter,
			String format) {
		
		if (isEmpty(stationId) && isEmpty(operatorStationId)) {
			throw new InvalidParameterException("Station id or Operator station id must be specified.");
		}
		
		StringBuilder vandahApiUrl = new StringBuilder(config.getVandahDmpApiUrl() + WATER_FLOWS + "?")
							.append( !isEmpty(stationId) ? "stationId=" + stationId : "")
							.append( !isEmpty(operatorStationId) ? "operatorStationId=" + operatorStationId : "")
							.append( !isEmpty(measurementPointNumber) ? "measurementPointNumber=" + measurementPointNumber : "")
							.append( !isEmpty(from) ? "from=" + from : "")
							.append( !isEmpty(to) ? "to=" + to : "")
							.append( !isEmpty(createdAfter) ? "createdAfter=" + createdAfter : "")
							.append( !isEmpty(format) ? "format=" + format : "");
		
		log.info("Call: " + vandahApiUrl.toString());
		
		DmpHydroApiResponsesMeasurementResultResponse[] results = restClient.get().uri(vandahApiUrl.toString())
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.onStatus(status -> status.value() >= 400, (request, response) -> {
			VandaHUtility.logAndPrint(log, Level.ERROR, true, "Error retrieving water flows: [" + response.getStatusCode() + "] " + response.getStatusText());
		})
		.body(DmpHydroApiResponsesMeasurementResultResponse[].class);
		
		return results;
		
	}
	
	private boolean isEmpty(Object obj) {
		return obj == null || (obj instanceof String && ((String) obj).isEmpty());
	}

}
