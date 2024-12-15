package dk.dataforsyningen.vanda_hydrometry_data.command;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.service.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesExaminationTypeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command to retrieve the Examination Types.
 *
 * @author Radu Dudici
 */
@Component
@CommandQualifier(command = "examinationtypes")
public class ExaminationTypesCommand implements CommandInterface {

  private static final Logger logger = LoggerFactory.getLogger(ExaminationTypesCommand.class);

  private final String ENDPOINT = "config/examination-types";

  @Autowired
  VandahDmpApiService vandahService;

  private DmpHydroApiResponsesExaminationTypeResponse[] data = null;

  @Autowired
  private VandaHydrometryDataConfig config;

  @Override
  public int getData() {
    data = vandahService.getExaminationTypes(config.getVandahDmpApiUrl() + ENDPOINT);

    if (data != null && data.length > 0) {
      return data.length;
    }

    return 0;
  }

  @Override
  public void mapData() {
    //nothing to map
  }

  @Override
  public int saveData() {
    logger.warn("Save to DB is not relevant for this data");
    return 0;
  }

  @Override
  public void displayData(boolean raw) {
    if (raw && data != null) {
      logger.info("Number of examinationTypes: " + data.length);
      for (DmpHydroApiResponsesExaminationTypeResponse examinationType : data) {
        System.out.println(examinationType);
      }
    }
  }

  @Override
  public void showShortHelp() {
    System.out.println(BOLD_ON + "examinationTypes" + FORMAT_OFF +
        " : retrieves the examination types with mapping and constraints.");
  }

  @Override
  public void showHelp() {
    showShortHelp();
  }
}
