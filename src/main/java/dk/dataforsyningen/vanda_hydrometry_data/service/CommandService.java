package dk.dataforsyningen.vanda_hydrometry_data.service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import dk.dataforsyningen.vanda_hydrometry_data.command.CommandInterface;
import dk.dataforsyningen.vanda_hydrometry_data.command.CommandQualifier;

/**
 * Retrieves all beans registered with @CommandQualifier annotation.
 * 
 * @author Radu Dudici
 */
@Service
public class CommandService implements ApplicationContextAware {

	private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("ID=" + this.applicationContext.getId());
    }

    /**
     * Returns all beans registered with @CommandQualifier for different commands
     * @return Map<String, CommandInterface>
     */
    public Map<String, CommandInterface> getAllCommandBeans() {
        // Get all beans of type CommandInterface from the context
        Map<String, CommandInterface> allCommands = applicationContext.getBeansOfType(CommandInterface.class);
        
        // Filter the beans to only those annotated with @CommandQualifier
        return allCommands.entrySet().stream()
                .filter(entry -> applicationContext.findAnnotationOnBean(entry.getKey(), CommandQualifier.class) != null)
                .collect(Collectors.toMap(entry -> {
                	CommandQualifier annotation = applicationContext.findAnnotationOnBean(entry.getKey(), CommandQualifier.class);
                	return annotation.command();
                }, Map.Entry::getValue));
                
                
    }
    
 
    /**
     * Returns the command bean associated with the given command or null otherwise
     * @param command 
     * @return CommandInterface bean
     */
    public CommandInterface getCommandBean(String command) {
	
        // Get all beans of type CommandInterface from the context
        Map<String, CommandInterface> allCommands = applicationContext.getBeansOfType(CommandInterface.class);

        // extract the bean registered for the given command
        try {
        	return allCommands.entrySet().stream()
                .filter(entry -> {
                	CommandQualifier annotation = applicationContext.findAnnotationOnBean(entry.getKey(), CommandQualifier.class);
                	return annotation != null && command != null && command.equals(annotation.command());
                }).findFirst().get().getValue();
        } catch (NoSuchElementException ex) {
        	return null;
        }
                
    }
    
}
