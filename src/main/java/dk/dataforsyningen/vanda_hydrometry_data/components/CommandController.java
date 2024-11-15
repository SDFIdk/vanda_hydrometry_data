package dk.dataforsyningen.vanda_hydrometry_data.components;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.command.CommandInterface;
import dk.dataforsyningen.vanda_hydrometry_data.service.CommandService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller to execute a command.
 *
 * @author Radu Dudici
 */
@Component
public class CommandController {

  private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

  @Autowired
  private CommandService commandService;

  @Autowired
  private VandaHydrometryDataConfig config;

  /**
   * Executes the command depending on the given options
   *
   * @param command
   */
  public void execute(CommandInterface commandBean) {
    logger.info("Execute command: " + commandBean.getClass().getSimpleName());

    if (config.isHelp()) {
      showHelp(commandBean, false);
    } else {
      int nr = commandBean.getData();
      logger.info("Read (" + nr + ") items.");

      commandBean.mapData();

      if (config.isDisplayData() || config.isDisplayRawData()) {
        commandBean.displayData(config.isDisplayRawData());
      }

      if (config.isSaveDb()) {
        nr = commandBean.saveData();
        logger.info("Save (" + nr + ") items to DB.");
      }
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
