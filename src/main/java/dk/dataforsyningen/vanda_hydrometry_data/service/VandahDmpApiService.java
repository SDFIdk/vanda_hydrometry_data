package dk.dataforsyningen.vanda_hydrometry_data.service;

import java.security.InvalidParameterException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.util.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.command.WaterFlowsCommand;
import dk.dataforsyningen.vanda_hydrometry_data.command.WaterLevelsCommand;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
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
	
	@Autowired
	private DatabaseService dbService;
	
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
	 * Call to API.
	 * 
	 * @param stationId - should be set to an actual station ID
	 * @param operatorStationId
	 * @param measurementPointNumber
	 * @param from
	 * @param to
	 * @param createdAfter
	 * @param format
	 * @return
	 */
	private DmpHydroApiResponsesMeasurementResultResponse[] getWaterLevelsForStation(
			String stationId, 
			String operatorStationId,
			Integer measurementPointNumber,
			OffsetDateTime from,
			OffsetDateTime to,
			OffsetDateTime createdAfter,
			String format
			) {
		
		if (isEmpty(stationId) && isEmpty(operatorStationId)) {
			throw new InvalidParameterException("Station id or Operator station id must be specified.");
		}

		String del = "";
		StringBuilder vandahApiUrl = new StringBuilder(config.getVandahDmpApiUrl() + WATER_LEVELS + "?");
		if (!isEmpty(stationId)) { vandahApiUrl.append(del + "stationId=" + stationId); del = "&"; }
		if (!isEmpty(operatorStationId)) { vandahApiUrl.append(del + "operatorStationId=" + operatorStationId); del = "&"; }
		if (!isEmpty(measurementPointNumber)) { vandahApiUrl.append(del + "measurementPointNumber=" + measurementPointNumber); del = "&"; }
		if (!isEmpty(from)) { vandahApiUrl.append(del + "from=" + from); del = "&"; }
		if (!isEmpty(to)) { vandahApiUrl.append(del + "to=" + to); del = "&"; }
		if (!isEmpty(createdAfter)) { vandahApiUrl.append(del + "createdAfter=" + createdAfter); del = "&"; }
		if (!isEmpty(format)) { vandahApiUrl.append(del + "format=" + format); del = "&"; }		
		
		VandaHUtility.logAndPrint(log, Level.INFO, config.isVerbose(), "Call: " + vandahApiUrl);
		
		DmpHydroApiResponsesMeasurementResultResponse[] results = restClient.get().uri(vandahApiUrl.toString())
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.onStatus(status -> status.value() >= 400, (request, response) -> {
			VandaHUtility.logAndPrint(log, Level.ERROR, false, "Error retrieving water levels: [" + response.getStatusCode() + "] " + response.getStatusText());
			String message = VandaHUtility.valueFromJson(response, "message");
			throw new InternalException("Error retrieving water levels: " + message);
		})
		.body(DmpHydroApiResponsesMeasurementResultResponse[].class);
		
		return results;
	}
	
	/**
	 * Retrieves water levels.
	 * 
	 * @param stationId can be "all", comma separated values, or an actual station Id
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
		
		if ("all".equalsIgnoreCase(stationId)) { //for all stations
			ArrayList<DmpHydroApiResponsesMeasurementResultResponse> output = new ArrayList<>();
			List<Station> stations = dbService.getAllStationsByExaminationType(WaterLevelsCommand.EXAMINATION_TYPE_SC);
			
			for(Station station : stations) {
				output.addAll(Arrays.asList(getWaterLevelsForStation(station.getStationId(), operatorStationId, measurementPointNumber, from, to, createdAfter, format)));
			}
			return output.toArray(new DmpHydroApiResponsesMeasurementResultResponse[output.size()]);
		} else if (stationId != null && stationId.indexOf(",") != -1) { //for selected stations
			String[] stationIds = stationId.split(",");
			ArrayList<DmpHydroApiResponsesMeasurementResultResponse> output = new ArrayList<>();
			
			for(String sId : stationIds) {
				if (dbService.isMeasurementSupported(sId, WaterLevelsCommand.EXAMINATION_TYPE_SC)) {
					output.addAll(Arrays.asList(getWaterLevelsForStation(sId, operatorStationId, measurementPointNumber, from, to, createdAfter, format)));
				}
			}
			return output.toArray(new DmpHydroApiResponsesMeasurementResultResponse[output.size()]);
			
			
		} else { //for one station
			return getWaterLevelsForStation(stationId, operatorStationId, measurementPointNumber, from, to, createdAfter, format);
		}
	}
	
	
	/**
	 * Call to API
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
	private DmpHydroApiResponsesMeasurementResultResponse[] getWaterFlowsForStation(
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
		StringBuilder vandahApiUrl = new StringBuilder(config.getVandahDmpApiUrl() + WATER_FLOWS + "?");
		if (!isEmpty(stationId)) { vandahApiUrl.append(del + "stationId=" + stationId); del = "&"; }
		if (!isEmpty(operatorStationId)) { vandahApiUrl.append(del + "operatorStationId=" + operatorStationId); del = "&"; }
		if (!isEmpty(measurementPointNumber)) { vandahApiUrl.append(del + "measurementPointNumber=" + measurementPointNumber); del = "&"; }
		if (!isEmpty(from)) { vandahApiUrl.append(del + "from=" + from); del = "&"; }
		if (!isEmpty(to)) { vandahApiUrl.append(del + "to=" + to); del = "&"; }
		if (!isEmpty(createdAfter)) { vandahApiUrl.append(del + "createdAfter=" + createdAfter); del = "&"; }
		if (!isEmpty(format)) { vandahApiUrl.append(del + "format=" + format); del = "&"; }
		
		VandaHUtility.logAndPrint(log, Level.INFO, config.isVerbose(), "Call: " + vandahApiUrl);
		
		DmpHydroApiResponsesMeasurementResultResponse[] results = restClient.get().uri(vandahApiUrl.toString())
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.onStatus(status -> status.value() >= 400, (request, response) -> {
			VandaHUtility.logAndPrint(log, Level.ERROR, false, "Error retrieving water flows: [" + response.getStatusCode() + "] " + response.getStatusText());
			String message = VandaHUtility.valueFromJson(response, "message");
			throw new InternalException("Error retrieving water flows: " + message);
		})
		.body(DmpHydroApiResponsesMeasurementResultResponse[].class);
		
		return results;
		
	}
	
	/**
	 * Retrieves water levels.
	 * 
	 * @param stationId can be "all", comma separated values, or an actual station Id
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
		
		if ("all".equalsIgnoreCase(stationId)) { //for all stations
			ArrayList<DmpHydroApiResponsesMeasurementResultResponse> output = new ArrayList<>();
			List<Station> stations = dbService.getAllStationsByExaminationType(WaterFlowsCommand.EXAMINATION_TYPE_SC);
			
			for(Station station : stations) {
				output.addAll(Arrays.asList(getWaterFlowsForStation(station.getStationId(), operatorStationId, measurementPointNumber, from, to, createdAfter, format)));
			}
			return output.toArray(new DmpHydroApiResponsesMeasurementResultResponse[output.size()]);
			
		} else if (stationId != null && stationId.indexOf(",") != -1) { //for selected stations
			String[] stationIds = stationId.split(",");
			ArrayList<DmpHydroApiResponsesMeasurementResultResponse> output = new ArrayList<>();
			
			for(String sId : stationIds) {
				if (dbService.isMeasurementSupported(sId, WaterFlowsCommand.EXAMINATION_TYPE_SC)) {
					output.addAll(Arrays.asList(getWaterFlowsForStation(sId, operatorStationId, measurementPointNumber, from, to, createdAfter, format)));
				}
			}
			return output.toArray(new DmpHydroApiResponsesMeasurementResultResponse[output.size()]);
			
		} else { //for one station
			return getWaterFlowsForStation(stationId, operatorStationId, measurementPointNumber, from, to, createdAfter, format);
		}
	}
	
	private boolean isEmpty(Object obj) {
		return obj == null || (obj instanceof String && ((String) obj).isEmpty());
	}

}
