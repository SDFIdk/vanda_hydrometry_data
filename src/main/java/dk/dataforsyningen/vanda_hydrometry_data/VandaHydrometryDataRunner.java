package dk.dataforsyningen.vanda_hydrometry_data;

import java.util.ArrayList;
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
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;

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
		
	@Override
	public void run(String... args) throws Exception {
		
		if (config.getVandahDmpApiUrl() == null) {
			log.warn("There was no API URL defined. Have you forgotten to choose a configurat profile? (use dev, prod, test)");
			return;
		}
		
		log.info("Using DMP API URL: " + config.getVandahDmpApiUrl() );
		log.debug(config.toString());
		
		commandLineArgsParser.parse(args);
		
		ArrayList<String> cmds = commandLineArgsParser.getCommands();
		
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
			
			VandaHUtility.logAndPrint(null, null, true, "Use the option --verbose to display the results in the console.");
			VandaHUtility.logAndPrint(null, null, true, "Use the option --saveDb to save the results in the defined database.");
		
		} else if (cmds.size() > 1) {
			log.warn("Too many commands requested.");
		
		}  else { //only one command
			commandController.execute(cmds.get(0));
		}
		
	}
	
}
