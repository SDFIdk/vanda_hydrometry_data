package dk.dataforsyningen.vanda_hydrometry_data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import dk.dataforsyningen.vanda_hydrometry_data.services.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesExaminationTypeResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;

@Component
public class VandaHydrometryDataRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(VandaHydrometryDataRunner.class);
	
	@Autowired
	RestClient restClient;
	
	@Autowired
	VandaHydrometryDataConfig config;
	
	@Autowired
	VandahDmpApiService vandahService;
	
	@Autowired
	CommandLineArgsParser commandLineArgsParser;
		
	@Override
	public void run(String... args) throws Exception {
		
		if (config.getVandahDmpApiUrl() == null) {
			log.warn("There was no API URL defined. Have you forgotten to choose a configurat profile? (use dev, prod, test)");
			return;
		}
		
		log.info("Using DMP API URL: " + config.getVandahDmpApiUrl() );
		
		commandLineArgsParser.parse(args);
		
		if (commandLineArgsParser.containsParam("stations")) {
			log.info("Execute command get stations");
			
			DmpHydroApiResponsesStationResponse[] stations = vandahService.getAllStations(); 
			
			log.info("count stations: " + stations.length);
			log.info("1st station: " + stations[0].toString());	
		} else if (commandLineArgsParser.containsParam("examinationtype")) {
			log.info("Execute command get examination types");
			
			DmpHydroApiResponsesExaminationTypeResponse[] types = vandahService.getExaminationTypes();
			log.info("count types: " + types.length);
			log.info("1st type: " + types[0].toString());
		} else {
			log.info("Usage: ...");
		}
	}
	
}
