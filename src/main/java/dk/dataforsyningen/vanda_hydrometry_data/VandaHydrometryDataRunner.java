package dk.dataforsyningen.vanda_hydrometry_data;

import dk.dataforsyningen.vanda_hydrometry_data.command.CommandInterface;
import dk.dataforsyningen.vanda_hydrometry_data.components.CommandController;
import dk.dataforsyningen.vanda_hydrometry_data.components.CommandLineArgsParser;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import dk.dataforsyningen.vanda_hydrometry_data.service.CommandService;
import dk.dataforsyningen.vanda_hydrometry_data.service.DatabaseService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Command line runner class that handles the input commands and delegates the execution.
 *
 * @author Radu Dudici
 */
@Component
public class VandaHydrometryDataRunner implements CommandLineRunner {

  private static Logger logger = LoggerFactory.getLogger(VandaHydrometryDataRunner.class);

  @Autowired
  VandaHydrometryDataConfig config;

  @Autowired
  CommandLineArgsParser commandLineArgsParser;

  @Autowired
  CommandController commandController;
  @Autowired
  DatabaseService databaseService;
  @Autowired
  private CommandService commandService;

  @Override
  public void run(String... args) {

    logger.debug("Application start ...");

    if (config.getVandahDmpApiUrl() == null) {
      logger.warn("There is no API URL defined in the properties file.");
      return;
    }

    logger.info("Using DMP API URL: " + config.getVandahDmpApiUrl());
    logger.debug(config.toString());

    commandLineArgsParser.parse(args);

    ArrayList<String> cmds = commandLineArgsParser.getCommands();

    // No command => display help
    if (cmds.isEmpty()) {
      System.out.println(
          "Vanda Hydrometry Data\n=====================\nUsage parameters: COMMAND [--options[=value]]");
      System.out.println("Commands:\n");

      Map<String, CommandInterface> allCommands = commandController.getAllCommandBeans();
      if (allCommands != null) {
        for (String command : allCommands.keySet()) {
          commandController.showHelp(command, true);
          System.out.println("For more details use: " + command + " --help\n");
        }
      }

      System.out.println(
          "Use the option --displayRawData to display the API values at the console.");
      System.out.println("Use the option --displayData to display the mapped data at the console.");
      System.out.println("Use the option --saveDb to save the values in the defined database.");

      // Too many commands
    } else if (cmds.size() > 1) {
      logger.warn("Too many commands requested.");

    } else { //only one command
      String cmd = cmds.getFirst();
      CommandInterface commandBean = commandService.getCommandBean(cmd);
      if (commandBean != null) {
        try {
          //handle special case when more stations are required
          //however, filter the station by the requested examinationTypeSc if necessary
          if ("all".equalsIgnoreCase(
              config.getStationId())) { //execute command for all relevant stations
            List<Station> stations = commandBean.getExaminationTypeSc() == 0 ?
                databaseService.getAllStations()
                :
                databaseService.getAllStationsByExaminationType(commandBean.getExaminationTypeSc());
            if (!stations.isEmpty()) {
              int i = 0;
              for (Station station : stations) {
                logger.info((++i) + "/" + stations.size() + "stations");
                config.setStationId(station.getStationId());
                commandController.execute(commandBean);
              }
            } else {
              logger.warn("No saved station can provide examination_type_sc=" +
                  commandBean.getExaminationTypeSc());
            }
          } else if (config.getStationId() != null &&
              config.getStationId().contains(",")) { //execute command for selected stations
            String[] stationIds = config.getStationId().split(",");
            boolean executed = false;
            int i = 0;
            for (String stationId : stationIds) {
              logger.info((++i) + "/" + stationIds.length + "stations");
              if (commandBean.getExaminationTypeSc() == 0 ||
                  databaseService.isMeasurementSupported(stationId,
                      commandBean.getExaminationTypeSc())) {
                config.setStationId(stationId);
                commandController.execute(commandBean);
                executed = true;
              } else {
                logger.warn("Measurement " + commandBean.getExaminationTypeSc() +
                    " not supported by station " + stationId);
              }
            }
            if (!executed) {
              logger.warn(
                  "The requested stations do not exist or they cannot provide examinatnion_type_sc=" +
                      commandBean.getExaminationTypeSc());
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
