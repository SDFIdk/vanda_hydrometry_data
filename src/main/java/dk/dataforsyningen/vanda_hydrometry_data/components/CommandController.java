package dk.dataforsyningen.vanda_hydrometry_data.components;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
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
	
	@Autowired
	private VandaHydrometryDataConfig config;

	/**
	 * Executes the command depending on the given options
	 * 
	 * @param command
	 */
    public void execute(String command) {
    	VandaHUtility.logAndPrint(log, Level.INFO, config.isVerbose(), "Execute command: " + command);
    	
    	CommandInterface commandBean = commandService.getCommandBean(command);
    	if (commandBean != null) {
    		
    		if (config.isHelp()) {
    			showHelp(commandBean, false);
    		} else {
	    		int nr = commandBean.getData();
	    		VandaHUtility.logAndPrint(null, null, config.isVerbose(), "Read (" + nr + ") items.");
	    		
	    		commandBean.mapData();
	    		
	    		if (config.isDisplayData() || config.isDisplayRawData()) {
	    			commandBean.displayData(config.isDisplayRawData());
	    		}
	    		
	    		if (config.isSaveDb()) {
	    			nr = commandBean.saveData();
	    			VandaHUtility.logAndPrint(null, null, config.isVerbose(), "Save (" + nr + ") items to DB.");
	    		}
    		}
    	} else {
    		VandaHUtility.logAndPrint(log, Level.ERROR, true, "No execution bean was regsitered for the given command: " + command);
    	}
    }
    
    public Map<String, CommandInterface> getAllCommandBeans() {
    	return commandService.getAllCommandBeans();
    }
    
    /**
     * Displays the command help.
     * 
     * @param command
     * @param shortHelp
     */
    public void showHelp(String command, boolean shortHelp) {
    	CommandInterface commandBean = commandService.getCommandBean(command);
    	showHelp(commandBean, shortHelp);
    }
    
    private void showHelp(CommandInterface commandBean, boolean shortHelp) {
    	if (commandBean != null) {
    		if (shortHelp) {
    			commandBean.showShortHelp();
    		} else {
    			commandBean.showHelp();
    		}
    	}    	
    }
    
}
