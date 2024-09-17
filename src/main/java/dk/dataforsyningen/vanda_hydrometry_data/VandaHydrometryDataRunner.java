package dk.dataforsyningen.vanda_hydrometry_data;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
		
		commandLineArgsParser.parse(args);
		
		ArrayList<String> cmds = commandLineArgsParser.getCommands();
		
		if (cmds.size() == 0) {
			log.info("Usage: ...");
		} else if (cmds.size() > 1) {
			log.warn("Too many commands requested");
		}  else { //only one command
			commandController.execute(cmds.get(0));
		}
		
	}
	
}
