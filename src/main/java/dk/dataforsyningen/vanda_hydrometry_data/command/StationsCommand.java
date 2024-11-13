package dk.dataforsyningen.vanda_hydrometry_data.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import dk.dataforsyningen.vanda_hydrometry_data.mapper.StationModelMapper;
import dk.dataforsyningen.vanda_hydrometry_data.service.DatabaseService;
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

	private final String ENDPOINT = "stations";
	
	private static final Logger logger = LoggerFactory.getLogger(StationsCommand.class);
	
	private DmpHydroApiResponsesStationResponse[] data;
	
	private ArrayList<Station> stations = new ArrayList<>();
	
	@Autowired
	VandahDmpApiService vandahService;
	
	@Autowired
	private VandaHydrometryDataConfig config;
	
	@Autowired
	private DatabaseService dbService;
	
	@Override
	public int getData() {
		
		data = vandahService.getStations(config.getVandahDmpApiUrl() + ENDPOINT, 
				config.getStationId(), 
				config.getOperatorStationId(), 
				config.getParameterSc(), 
				config.getExaminationTypeSc(), 
				config.getWithResultsAfter(), 
                config.getWithResultsCreatedAfter());

		if (data != null && data.length > 0) {
			return data.length;
		}
		return 0;
	}

	@Override
	public void mapData() {
		if (data != null) {
			stations = Arrays.stream(data) //make the array of stations responses into a Stream<DmpHydroApiResponsesStationResponse>
					.map(response -> StationModelMapper.from(response)) //map the stream of stations into a Stream<Stations>
					.collect(Collectors.toCollection(ArrayList::new)); //convert to List<Stations>
		}
	}

	@Override
	public int saveData() {
		if (data != null) {
			dbService.saveStations(stations);
		}
		return data != null ? data.length : 0;
	}

	@Override
	public void displayData(boolean raw) {
		if (raw && data != null) {
   			logger.info("Number of items: " + data.length);

			for(DmpHydroApiResponsesStationResponse item : data) {
				System.out.println(item);
			}
		}
		if (!raw && stations != null) {
			logger.info("Number of stations: " + stations.size());

			for(Station item : stations) {
				System.out.println(item);
			}
		}
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
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "withResultsAfter" + VandaHUtility.FORMAT_OFF + " : only return stations with examinations that got results measured after a point in time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component \"Z\" (Zulu) is not provided, the system's time zone is considered.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "withResultsCreatedAfter" + VandaHUtility.FORMAT_OFF + " : only return stations with examination that contains results created after a point in time. This is the point in time there where created/updated in the system and not the actual measurement time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component \"Z\" (Zulu) is not provided, the system's time zone is considered.");		
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

}
