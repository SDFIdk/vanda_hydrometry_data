package dk.dataforsyningen.vanda_hydrometry_data.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.service.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;

/**
 * Command to retrieve the stations.
 * 
 * @author Radu Dudici
 */
@Component
@CommandQualifier(command = "stations")
public class StationsCommand implements CommandInterface {

	private static final Logger log = LoggerFactory.getLogger(StationsCommand.class);
	
	@Autowired
	VandahDmpApiService vandahService;
	
	@Override
	public void getData() {
		DmpHydroApiResponsesStationResponse[] stations = vandahService.getAllStations(); 
		
		log.info("count stations: " + stations.length);
		log.info("1st station: " + stations[0].toString());
	}

	@Override
	public void mapData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub

	}

}
