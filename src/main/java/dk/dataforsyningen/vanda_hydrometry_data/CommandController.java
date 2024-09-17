package dk.dataforsyningen.vanda_hydrometry_data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.command.CommandInterface;
import dk.dataforsyningen.vanda_hydrometry_data.service.CommandService;

/**
 * Controller to execute a command.
 * 
 * @author Radu Dudici
 */
@Component
public class CommandController {
	
	private static final Logger log = LoggerFactory.getLogger(CommandController.class);
	
	@Autowired
	private CommandService commandService;

    public void execute(String command) {
    	log.info("Execute command: " + command);
    	CommandInterface commandBean = commandService.getCommandBean(command);
    	if (commandBean != null) {
    		commandBean.getData();
    		
    		commandBean.mapData();
    		
    		commandBean.saveData();
    	} else {
    		log.error("No execution bean was regsitered for the given command: " + command);
    	}
    }
    
}
