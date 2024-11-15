package dk.dataforsyningen.vanda_hydrometry_data.components;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataApplication;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Parse the command line parameters and extract a list of commands.
 * Commands are simple strings.
 * Options starts with "--" and are disconsidered - they are parsed by the @Configuration component.
 *
 * @author Radu Dudici
 */
@Component
public class CommandLineArgsParser {

  private static final Logger log = LoggerFactory.getLogger(CommandLineArgsParser.class);

  private ArrayList<String> commands;

  public CommandLineArgsParser() {
    this.clear();
  }

  public void clear() {
    commands = new ArrayList<>();
  }

  public void parse(String... args) {
    for (String arg : args) {
      if (arg != null && arg.length() > 0 && !arg.startsWith("--")) {
        //do not consider application's startup class as a command.
        if (!arg.equalsIgnoreCase(VandaHydrometryDataApplication.class.getCanonicalName())) {
          commands.add(arg.toLowerCase());
          log.debug("Added command '" + arg.toLowerCase() + "'");
        }
      }
    }
  }

  public ArrayList<String> getCommands() {
    return commands;
  }

  /**
   * Only checks that a command with the given name exists
   *
   * @param name
   * @return
   */
  public boolean hasCommand(String name) {
    return commands.contains(name.toLowerCase());
  }

}
