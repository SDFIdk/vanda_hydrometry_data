package dk.dataforsyningen.vanda_hydrometry_data.command;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.mapper.MeasurementModelMapper;
import dk.dataforsyningen.vanda_hydrometry_data.mapper.MeasurementTypeModelMapper;
import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.service.DatabaseService;
import dk.dataforsyningen.vanda_hydrometry_data.service.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesMeasurementResultResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command to retrieve the stream discharge (endpoint: water-flows).
 *
 * @author Radu Dudici
 */
@Component
@CommandQualifier(command = "streamdischarge")
public class StreamDischargeCommand implements CommandInterface {

  public static final int EXAMINATION_TYPE_SC = 27;

  private static final Logger logger = LoggerFactory.getLogger(StreamDischargeCommand.class);

  private final String ENDPOINT = "water-flows";

  @Autowired
  VandahDmpApiService vandahService;

  private DmpHydroApiResponsesMeasurementResultResponse[] data;

  private ArrayList<Measurement> measurements;

  private ArrayList<MeasurementType> measurementTypes;

  @Autowired
  private VandaHydrometryDataConfig config;

  @Autowired
  private DatabaseService dbService;

  @Override
  public int getData() {
    data = vandahService.getMeasurementsForStation(config.getVandahDmpApiUrl() + ENDPOINT,
        config.getStationId(),
        config.getOperatorStationId(),
        config.getMeasurementPointNumber(),
        config.getFrom(),
        config.getTo(),
        config.getCreatedAfter()
    );

    //count measurements
    int nr = 0;
    if (data != null) {
      for (DmpHydroApiResponsesMeasurementResultResponse station : data) {
        if (station.getResults() != null) {
          nr += station.getResults().size();
        }
      }
    }

    return nr;
  }

  @Override
  public void mapData() {
    if (data != null) {
      measurementTypes = new ArrayList<>();
      //map the response for each station into a Stream<Measurement>.
      measurements = Stream.of(
              data) //make the array of responses into a Stream<DmpHydroApiResponsesMeasurementResultResponse>
          .filter(response -> response != null &&
              response.getResults() != null) //disconsider null elements if any
          .flatMap(
              response -> response.getResults()
                  .stream() //make the results for each station into a Stream<DmpHydroApiResponsesResultResponse>
                  .map(result -> {

                    //Create the Measurement Type too
                    MeasurementType mt = MeasurementTypeModelMapper.from(result);
                    if (!measurementTypes.contains(mt)) {
                      measurementTypes.add(mt);
                    }

                    return MeasurementModelMapper.from(result, response.getStationId());
                  }) //map the array of Results into Stream<Measurements>
          ) //Flatmap flattens the streams of streams into a single stream of measurements Stream<Measurements>
          .collect(Collectors.toCollection(ArrayList::new)); //collect all into a List<Measurements>
    }
  }

  /**
   * Inserts or updates the measurements.
   * As a side effect it inserts or updates the measurement types.
   */
  @Override
  public int saveData() {
    if (measurements != null && measurementTypes != null) {
      //save the measurement types first
      dbService.addMeasurementTypes(measurementTypes);

      //save the measurements
      dbService.saveMeasurements(measurements);
    }

    if (measurements != null) {
      return measurements.size();
    }

    return 0;
  }

  @Override
  public void displayData(boolean raw) {
    if (raw && data != null) {
      logger.info("Number of items: " + data.length);

      for (DmpHydroApiResponsesMeasurementResultResponse item : data) {
        System.out.println(item);
      }
    }
    if (!raw && measurements != null && measurementTypes != null) {
      logger.info("Number of measurements: " + measurements.size());
      logger.info("Number of measurementTypes: " + measurementTypes.size());

      for (Measurement item : measurements) {
        System.out.println(item);
      }
      for (MeasurementType item : measurementTypes) {
        System.out.println(item);
      }
    }
  }

  @Override
  public void showShortHelp() {
    System.out.println(BOLD_ON + "streamDischarge" + FORMAT_OFF +
        " : Retrieves current values of stream discharge (ExaminationType 27) measurements.");
  }

  @Override
  public void showHelp() {
    showShortHelp();
    System.out.println(
        "Options: --stationId=number [--operatorStationId=string] [--measurementPointNumber=number] [--from=date] [--to=date] [--createdAfter=date]");

    System.out.println("\t" + ITALIC_ON + "stationId" + FORMAT_OFF +
        " :  is a 8 digits number to identify a single station. Either stationId or operatorStationId must be provided. Use \"all\" (for ex. --stationId=all) to read data for all stations saved in the database. Use comma separated values (f.ex. --stationId=10000002,10000003) to read data for selected stations.");
    System.out.println(
        "\t" + ITALIC_ON + "operatorStationId" + FORMAT_OFF +
            " :  the id of the stations' operator. Either stationId or operatorStationId must be provided.");
    System.out.println(
        "\t" + ITALIC_ON + "measurementPointNumber" + FORMAT_OFF +
            " :  the measurement point number on the station. If not specified, returns all measurement points.");
    System.out.println("\t" + ITALIC_ON + "from" + FORMAT_OFF +
        " :  from measurement date time to include in the response. Return values on the specified date time and later. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component \"Z\" (Zulu) is not provided, the system's time zone is considered.");
    System.out.println("\t" + ITALIC_ON + "to" + FORMAT_OFF +
        " :  to measurement date time to include in the response. Return values on the specified date time and ealier. Both From and To must be specified if one of them presents. If -from/-to is not specified, it returns data for the last 24 hours. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component \"Z\" (Zulu) is not provided, the system's time zone is considered.");
    System.out.println("\t" + ITALIC_ON + "createdAfter" + FORMAT_OFF +
        " :  return values that are created or updated after the specified date time. Should be used together with 'from' and 'to' parameters. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'. If the time zone component \"Z\" (Zulu) is not provided, the system's time zone is considered.");
  }

  public ArrayList<Measurement> getMeasurements() {
    return measurements;
  }

  public ArrayList<MeasurementType> getMeasurementTypes() {
    return measurementTypes;
  }

  @Override
  public int getExaminationTypeSc() {
    return EXAMINATION_TYPE_SC;
  }
}
