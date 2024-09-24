package dk.dataforsyningen.vanda_hydrometry_data.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
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

	private static final Logger log = LoggerFactory.getLogger(WaterFlowsCommand.class);
	
	private DmpHydroApiResponsesMeasurementResultResponse[] data;
	
	@Autowired
	VandahDmpApiService vandahService;
	
	@Autowired
	private VandaHydrometryDataConfig config;

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
		return data != null ? data.length : 0;
	}

	@Override
	public void mapData() {
		if (data != null) {
			// TODO Auto-generated method stub
		}
	}

	@Override
	public int saveData() {
		if (data != null) {
			// TODO Auto-generated method stub
		}
		return data != null ? data.length : 0;
	}

	@Override
	public void displayData() {
		if (data != null) {
			VandaHUtility.logAndPrint(null, null, config.isVerbose(), "Number of items: " + data.length);
			for(DmpHydroApiResponsesMeasurementResultResponse item : data) {
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

		System.out.println("\t" + VandaHUtility.ITALIC_ON + "stationId" + VandaHUtility.FORMAT_OFF + " :  is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "operatorStationId" + VandaHUtility.FORMAT_OFF + " :  the id of the stations' operator. Either stationId or operatorStationId must be provided.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "measurementPointNumber" + VandaHUtility.FORMAT_OFF + " :  the measurement point number on the station. If not specified, returns all measurement points.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "from" + VandaHUtility.FORMAT_OFF + " :  from measurement date time to include in the response. Return results on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "to" + VandaHUtility.FORMAT_OFF + " :  to measurement date time to include in the response. Return results on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
		System.out.println("\t" + VandaHUtility.ITALIC_ON + "createdAfter" + VandaHUtility.FORMAT_OFF + " :  return results that are created or updated after the specified date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
	}

}