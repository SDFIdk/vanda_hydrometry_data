package dk.dataforsyningen.vanda_hydrometry_data.command;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.service.DatabaseService;
import dk.dataforsyningen.vanda_hydrometry_data.service.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesMeasurementResultResponse;

/**
 * Command to retrieve the water flows.
 * 
 * @author Radu Dudici
 */
@Component
@CommandQualifier(command = "waterflows")
public class WaterFlowsCommand implements CommandInterface {
	
	public static final int EXAMINATION_TYPE_SC = 27;

	private static final Logger log = LoggerFactory.getLogger(WaterFlowsCommand.class);
	
	private DmpHydroApiResponsesMeasurementResultResponse[] data;
	
	private ArrayList<Measurement> measurements;
	
	private ArrayList<MeasurementType> measurementTypes;
	
	@Autowired
	VandahDmpApiService vandahService;
	
	@Autowired
	private VandaHydrometryDataConfig config;
	
	@Autowired
	private DatabaseService dbService;

	@Override
	public int getData() {
		data = vandahService.getWaterFlows(config.getStationId(),
				config.getOperatorStationId(),
				config.getMeasurementPointNumber(),
				config.getFrom(),
				config.getTo(),
				config.getCreatedAfter(),
				null
				);
		
		//count measurements
		int nr = 0;
		if (data != null) {
			for(DmpHydroApiResponsesMeasurementResultResponse station : data) {
				nr += (station.getResults() != null ? station.getResults().size() : 0);
			}
		}
		
		return nr;
	}

	@Override
	public void mapData() {
		if (data != null) {
			measurementTypes = new ArrayList<>();
			measurements = Stream.of(data) //make the array of responses into a Stream<DmpHydroApiResponsesMeasurementResultResponse>
				.filter(response -> response != null && response.getResults() != null) //disconsider null elements if any
				.map(
					response -> response.getResults().stream() //make the results for each station into a Stream<DmpHydroApiResponsesResultResponse>
								.map( result -> {
									
									//Create the Measurement Type too
									MeasurementType mt = VandaHUtility.measurementTypeFrom(result);
									if (!measurementTypes.contains(mt)) {
										measurementTypes.add(mt);
									}
									
									return VandaHUtility.measurementFrom( result , response.getStationId()); 
								}) //map the array of Results into Stream<Measurements>
				) //map the response for each station into a Stream<Measurement>. the results is Stream<Stream<Measurements>> 
				.flatMap(Function.identity()) //flatten the streams of streams into a single stream of measurements
				.collect(Collectors.toCollection(ArrayList::new)); //collect all into a List<Measurements>
		}
	}

	/**
	 * Inserts or updates the measurements.
	 * As a side effect it inserts or updates the measurement types.
	 */
	@Override
	public int saveData() {
		if (measurements != null && measurementTypes != null) {
			//save the measurement types first
			dbService.addMeasurementTypes(measurementTypes);
			
			//save the measurements
			dbService.saveMeasurements(measurements);
		}
		return measurements != null ? measurements.size() : 0;
	}

	@Override
	public void displayData(boolean raw) {
		if (raw && data != null) {
			VandaHUtility.logAndPrint(null, null, config.isVerbose(), "Number of items: " + data.length);
			for(DmpHydroApiResponsesMeasurementResultResponse item : data) {
				System.out.println(item);
			}
		}
		if (!raw && measurements != null && measurementTypes != null) {
			VandaHUtility.logAndPrint(null, null, config.isVerbose(), "Number of measurements: " + measurements.size());
			VandaHUtility.logAndPrint(null, null, config.isVerbose(), "Number of measurementTypes: " + measurementTypes.size());
			for(Measurement item : measurements) {
				System.out.println(item);
			}
			for(MeasurementType item : measurementTypes) {
				System.out.println(item);
			}
		}
	}

	@Override
	public void showShortHelp() {
		System.out.println(VandaHUtility.BOLD_ON + "waterFlows" + VandaHUtility.FORMAT_OFF + " : Retrieves current results of water flows (ExaminationType 27) measurements.");
	}

	@Override
	public void showHelp() {
		showShortHelp();
		System.out.println("Options: --stationId=number [--operatorStationId=string] [--measurementPointNumber=number] [--from=date] [--to=date] [--createdAfter=date]");

		System.out.println("\t" + VandaHUtility.ITALIC_ON + "stationId" + VandaHUtility.FORMAT_OFF + " :  is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided. Use \"all\" (for ex. --stationId=all) to read data for all stations saved in the database. Use comma separated values (f.ex. --stationId=10000002,10000003) to read data for selected stations.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "operatorStationId" + VandaHUtility.FORMAT_OFF + " :  the id of the stations' operator. Either stationId or operatorStationId must be provided.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "measurementPointNumber" + VandaHUtility.FORMAT_OFF + " :  the measurement point number on the station. If not specified, returns all measurement points.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "from" + VandaHUtility.FORMAT_OFF + " :  from measurement date time to include in the response. Return results on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component \"Z\" (Zulu) is not provided, the system's time zone is considered.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "to" + VandaHUtility.FORMAT_OFF + " :  to measurement date time to include in the response. Return results on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component \"Z\" (Zulu) is not provided, the system's time zone is considered.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "createdAfter" + VandaHUtility.FORMAT_OFF + " :  return results that are created or updated after the specified date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component \"Z\" (Zulu) is not provided, the system's time zone is considered.");
	}

	public ArrayList<Measurement> getMeasurements() {
		return measurements;
	}

	public ArrayList<MeasurementType> getMeasurementTypes() {
		return measurementTypes;
	}
	
	@Override
	public int getExaminationTypeSc() {
		return EXAMINATION_TYPE_SC;
	}

}
