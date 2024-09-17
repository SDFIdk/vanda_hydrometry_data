package dk.dataforsyningen.vanda_hydrometry_data.service;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesExaminationTypeResponse;
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
	
	@Autowired
	RestClient restClient;
	
	@Autowired
	VandaHydrometryDataConfig config;
	
	public DmpHydroApiResponsesStationResponse[] getAllStations() {

		String vandahApiUrl = config.getVandahDmpApiUrl() + STATIONS_PATH;
		
		log.info("Call: " + vandahApiUrl);
		/*
		ResponseEntity<String> response
			  = restTemplate.getForEntity(vandahApiUrl, String.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			log.info(root.toString());
		} else {
			log.error("Error accessing API");
		}
		*/
					
		
		/*ResponseEntity<DmpHydroApiResponsesStationResponse[]> responseEntity =
				restClient.get()
				.uri(vandahApiUrl)
				.retrieve()
				.toEntity(DmpHydroApiResponsesStationResponse[].class);
		DmpHydroApiResponsesStationResponse[] objects = responseEntity.getBody();*/
		
		DmpHydroApiResponsesStationResponse[] stations = restClient.get().uri(vandahApiUrl)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					log.error("Error retrieving stations: [" + response.getStatusCode() + "] " + response.getStatusText());
				})
				.body(DmpHydroApiResponsesStationResponse[].class);
				
		return stations;
	}
	
	public DmpHydroApiResponsesStationResponse[] getStations(
			String stationId, 
			String operatorStationId,
			String stationOwnerCvr,
			String operatorCvr,
			int parameterSc,
			int examinationTypeSc,
			OffsetDateTime withResultsAfter,
			OffsetDateTime withResultsCreatedAfter,
			String format
			) {
		return null;
	}
	
	public DmpHydroApiResponsesExaminationTypeResponse[] getExaminationTypes() {
		String vandahApiUrl = config.getVandahDmpApiUrl() + EXAMINATION_TYPES;
		
		log.info("Call: " + vandahApiUrl);
		
		DmpHydroApiResponsesExaminationTypeResponse[] types = restClient.get().uri(vandahApiUrl)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(status -> status.value() >= 400, (request, response) -> {
					log.error("Error retrieving examination types: [" + response.getStatusCode() + "] " + response.getStatusText());
				})
				.body(DmpHydroApiResponsesExaminationTypeResponse[].class);
				
		return types;
		
	}
	
	public void getWaterLevels() {
		
	}
	
	public void getWaterFlows() {
		
	}
	
	public void getMeasurementsCurrentResults() {
		
	}
	
	public void getMeasurementsFirstResults() {
		
	}

	public void getMeasurementsAllResults() {
	
	}
	
	public void getMeasurementsValidFromResults() {
		
	}

}
