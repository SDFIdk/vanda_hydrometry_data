package dk.dataforsyningen.vanda_hydrometry_data.command;

import java.lang.annotation.Retention;
import java.lang.annotation.*;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Annotate (bind) a "command" Bean/Component with the name of a command 
 * that can be provided in the command line. 
 * This map the command (from the command line) to the relevant API service 
 * and specific functionality.
 * 
 *  @author Radu Dudici
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface CommandQualifier {
	String command();
}
