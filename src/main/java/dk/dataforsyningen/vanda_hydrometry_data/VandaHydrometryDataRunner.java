package dk.dataforsyningen.vanda_hydrometry_data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import dk.dataforsyningen.vanda_hydrometry_data.command.CommandInterface;
import dk.dataforsyningen.vanda_hydrometry_data.components.CommandController;
import dk.dataforsyningen.vanda_hydrometry_data.components.CommandLineArgsParser;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import dk.dataforsyningen.vanda_hydrometry_data.service.CommandService;
import dk.dataforsyningen.vanda_hydrometry_data.service.DatabaseService;

/**
 * Command line runner class that handles the input commands and delegates the execution.
 * 
 * @author Radu Dudici
 */
@Component
public class VandaHydrometryDataRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(VandaHydrometryDataRunner.class);
	
	@Autowired
	RestClient restClient;
	
	@Autowired
	VandaHydrometryDataConfig config;
	
	@Autowired
	CommandLineArgsParser commandLineArgsParser;
	
	@Autowired
	CommandController commandController;
	
	@Autowired
	private CommandService commandService;
	
	@Autowired
	DatabaseService databaseService;
	
	@Override
	public void run(String... args) throws Exception {
		
		log.debug("Application start ...");
		
		if (config.getVandahDmpApiUrl() == null) {
			VandaHUtility.logAndPrint(log, Level.WARN, false, "There is no API URL defined in the properties file.");
			return;
		}
		
		log.info("Using DMP API URL: " + config.getVandahDmpApiUrl() );
		log.debug(config.toString());
		
		commandLineArgsParser.parse(args);
		
		ArrayList<String> cmds = commandLineArgsParser.getCommands();
		
		// No command => display help
		if (cmds.size() == 0) {
			VandaHUtility.logAndPrint(null, null, true, "Vanda Hydrometry Data\n=====================\nUsage parameters: COMMAND [--options[=value]]");
			VandaHUtility.logAndPrint(null, null, true, "Commands:\n");
			
			Map<String, CommandInterface> allCommands = commandController.getAllCommandBeans();
			if (allCommands != null) {
				for(String command : allCommands.keySet()) {
					commandController.showHelp(command, true);
					VandaHUtility.logAndPrint(null, null, true, "For more details use: " + command + " --help\n");
				}
			}
			
			VandaHUtility.logAndPrint(null, null, true, "Use the option --displayRawData to display the API results at the console.");
			VandaHUtility.logAndPrint(null, null, true, "Use the option --displayData to display the mapped data at the console.");
			VandaHUtility.logAndPrint(null, null, true, "Use the option --verbose to display more info at the console.");
			VandaHUtility.logAndPrint(null, null, true, "Use the option --saveDb to save the results in the defined database.");
		
		// Too many commands
		} else if (cmds.size() > 1) {
			VandaHUtility.logAndPrint(log, Level.WARN, false, "Too many commands requested.");
		
		}  else { //only one command
			String cmd = cmds.get(0);
			CommandInterface commandBean = commandService.getCommandBean(cmd);
			if (commandBean != null) {
				try {
					//handle special case when more stations are required
					//however, filter the station by the requested examinationTypeSc if necessary
					if ("all".equalsIgnoreCase(config.getStationId())) { //execute command for all relevant stations
						List<Station> stations =  commandBean.getExaminationTypeSc() == 0 ? 
									databaseService.getAllStations() 
									: databaseService.getAllStationsByExaminationType(commandBean.getExaminationTypeSc());
						for(Station station : stations) {
							config.setStationId(station.getStationId());
							commandController.execute(commandBean);
						}
					} else if (config.getStationId() != null && config.getStationId().indexOf(",") != -1) { //execute command for selected stations
						String[] stationIds = config.getStationId().split(",");
						for(String stationId : stationIds) {
							if (commandBean.getExaminationTypeSc() == 0 || databaseService.isMeasurementSupported(stationId, commandBean.getExaminationTypeSc())) {
								config.setStationId(stationId);
								commandController.execute(commandBean);
							}
						}
					} else { //execute command once
						commandController.execute(commandBean);
					}
				} catch (Exception ex) {
					VandaHUtility.logAndPrint(log, Level.ERROR, false, "Error executing command '" + cmd + "'", ex);
					System.exit(1);
				}
			} else {
	    		VandaHUtility.logAndPrint(log, Level.ERROR, false, "No execution bean was regsitered for the given command: " + cmd);
	    	}
		}
		
		log.debug("Application ended.");
		
	}
	
}
