package dk.dataforsyningen.vanda_hydrometry_data.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
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

	@Override
	public void displayData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showShortHelp() {
		System.out.println(VandaHUtility.BOLD_ON + "stations" + VandaHUtility.FORMAT_OFF + " : retrieves all or selected stations based on given criteria.");
	}

	@Override
	public void showHelp() {
		showShortHelp();
		System.out.println("Options: [--stationId=number] [--examinationTypeSc=number,number] [--operatorStationId=string] [--withResultsAfter=date] [--withResultsCreatedAfter=date]");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "stationId" + VandaHUtility.FORMAT_OFF + " : is a 8 digits number to identify a single station.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "operatorStationId" + VandaHUtility.FORMAT_OFF + " : the id of the stations' operator.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "examinationTypeSc" + VandaHUtility.FORMAT_OFF + " : retrieve the stations that provides the requested examination types. Can be a comma separated values (no spaces).");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "withResultsAfter" + VandaHUtility.FORMAT_OFF + " : only return stations with examinations that got results measured after a point in time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "withResultsCreatedAfter" + VandaHUtility.FORMAT_OFF + " : only return stations with examination that contains results created after a point in time. This is the point in time there where created/updated in the system and not the actual measurement time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");		
	}

}
