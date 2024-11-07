package dk.dataforsyningen.vanda_hydrometry_data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import dk.dataforsyningen.vanda_hydrometry_data.command.CommandInterface;
import dk.dataforsyningen.vanda_hydrometry_data.components.CommandController;
import dk.dataforsyningen.vanda_hydrometry_data.components.CommandLineArgsParser;
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

	private static Logger logger = LoggerFactory.getLogger(VandaHydrometryDataRunner.class);
	
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
		
		logger.debug("Application start ...");
		
		if (config.getVandahDmpApiUrl() == null) {
			logger.warn("There is no API URL defined in the properties file.");
			return;
		}
		
		logger.info("Using DMP API URL: " + config.getVandahDmpApiUrl() );
		logger.debug(config.toString());
		
		commandLineArgsParser.parse(args);
		
		ArrayList<String> cmds = commandLineArgsParser.getCommands();
		
		// No command => display help
		if (cmds.size() == 0) {
			System.out.println("Vanda Hydrometry Data\n=====================\nUsage parameters: COMMAND [--options[=value]]");
			System.out.println("Commands:\n");
			
			Map<String, CommandInterface> allCommands = commandController.getAllCommandBeans();
			if (allCommands != null) {
				for(String command : allCommands.keySet()) {
					commandController.showHelp(command, true);
					System.out.println("For more details use: " + command + " --help\n");
				}
			}
			
			System.out.println("Use the option --displayRawData to display the API results at the console.");
			System.out.println("Use the option --displayData to display the mapped data at the console.");
			System.out.println("Use the option --verbose to display more info at the console.");
			System.out.println("Use the option --saveDb to save the results in the defined database.");
		
		// Too many commands
		} else if (cmds.size() > 1) {
			logger.warn("Too many commands requested.");
		
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
					logger.error("Error executing command '" + cmd + "'", ex);
					System.exit(1);
				}
			} else {
				logger.error("No execution bean was regsitered for the given command: " + cmd);
	    	}
		}
		
		logger.debug("Application ended.");	
	}
	
}
