package dk.dataforsyningen.vanda_hydrometry_data;

import java.security.InvalidParameterException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class.
 * Reads and provides the configuration from properties file.
 *
 * @author Radu Dudici
 */
@Configuration
public class VandaHydrometryDataConfig {

  /* Values from the properties file */
  //enables DAO and database service testing - needs a DB connection
  @Value("${vanda-hydrometry-data.database.test:#{false}}")
  public boolean enableTest; //used only within testing
  @Value("${dmp.vandah.api.url:#{null}}")
  String vandahDmpApiUrl;

  /* Option values from the command line */
  @Value("${help:#{null}}")
  private String help; //boolean

  @Value("${displaydata:#{null}}")
  private String displayData;  //boolean

  @Value("${displayrawdata:#{null}}")
  private String displayRawData;  //boolean

  @Value("${savedb:#{null}}")
  private String saveDb;  //boolean

  @Value("${stationid:#{null}}")
  private String stationId;

  @Value("${examinationtypesc:#{null}}")
  private String examinationTypeSc;  //int

  @Value("${parametersc:#{null}}")
  private String parameterSc; //Integer

  @Value("${operatorstationid:#{null}}")
  private String operatorStationId;

  @Value("${measurementpointnumber:#{null}}")
  private String measurementPointNumber;  //Integer

  @Value("${withresultsafter:#{null}}")
  private String withResultsAfter; //date

  @Value("${withresultscreatedafter:#{null}}")
  private String withResultsCreatedAfter;  //date

  @Value("${from:#{null}}")
  private String from;  //date

  @Value("${to:#{null}}")
  private String to;  //date

  @Value("${createdafter:#{null}}")
  private String createdAfter;  //date


  /* Getters */

  public boolean isHelp() {
    return help != null;
  }

  public boolean isDisplayData() {
    return displayData != null;
  }

  public boolean isDisplayRawData() {
    return displayRawData != null;
  }

  public boolean isSaveDb() {
    return saveDb != null;
  }

  public Integer getExaminationTypeSc() {
    if (examinationTypeSc == null) {
        return null;
    }
    try {
      return Integer.parseInt(examinationTypeSc);
    } catch (NumberFormatException ex) {
      throw new NumberFormatException("Invalid data found in 'examinationTypeSc' parameter.");
    }
  }

  public Integer getParameterSc() {
    if (parameterSc == null) {
        return null;
    }
    try {
      return Integer.parseInt(parameterSc);
    } catch (NumberFormatException ex) {
      throw new NumberFormatException("Invalid data found in 'parameterSc' parameter.");
    }
  }

  public Integer getMeasurementPointNumber() {
    if (measurementPointNumber == null) {
        return null;
    }
    try {
      return Integer.parseInt(measurementPointNumber);
    } catch (NumberFormatException ex) {
      throw new NumberFormatException("Invalid data found in 'measurementPointNumber' parameter.");
    }
  }

  public OffsetDateTime getWithResultsAfter() {
    if (withResultsAfter == null) {
        return null;
    }
    try {
      return OffsetDateTime.parse(withResultsAfter);
    } catch (DateTimeParseException | NullPointerException exception) {
      throw new InvalidParameterException(
          "Invalid date format found in 'withResultsAfter' parameter: " + exception.getMessage() + ". Remember that it must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
    }
  }

  public OffsetDateTime getWithResultsCreatedAfter() {
    if (withResultsCreatedAfter == null) {
        return null;
    }
    try {
      return OffsetDateTime.parse(withResultsCreatedAfter);
    } catch (DateTimeParseException | NullPointerException exception) {
      throw new InvalidParameterException(
          "Invalid date format found in 'withResultsCreatedAfter' parameter: " + exception.getMessage() + ". Remember that it must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
    }
  }

  public OffsetDateTime getFrom() {
    if (from == null) {
        return null;
    }
    try {
      return OffsetDateTime.parse(from);
    } catch (DateTimeParseException | NullPointerException exception) {
      throw new InvalidParameterException(
          "Invalid date format found in 'from' parameter: " + exception.getMessage() + ". Remember that it must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
    }
  }

  public OffsetDateTime getTo() {
    if (to == null) {
        return null;
    }
    try {
      return OffsetDateTime.parse(to);
    } catch (DateTimeParseException | NullPointerException exception) {
      throw new InvalidParameterException(
          "Invalid date format found in 'to' parameter: " + exception.getMessage() + ". Remember that it must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
    }
  }

  public OffsetDateTime getCreatedAfter() {
    if (createdAfter == null) {
        return null;
    }
    try {
      return OffsetDateTime.parse(createdAfter);
    } catch (DateTimeParseException | NullPointerException exception) {
      throw new InvalidParameterException(
          "Invalid date format found in 'to' parameter: " + exception.getMessage() + ". Remember that it must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.");
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Configuration \"param: (input_value) parssed_value\" :\n");

    sb.append("\tHelp: ").append("(" + help + ") ");
    try {
      sb.append(isHelp());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tDisplayRawData: ").append("(" + displayRawData + ") ");
    try {
      sb.append(isDisplayRawData());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tDisplayData: ").append("(" + displayData + ") ");
    try {
      sb.append(isDisplayData());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tSaveDb: ").append("(" + saveDb + ") ");
    try {
      sb.append(isSaveDb());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tStationId: ").append("(" + stationId + ") ");
    try {
      sb.append(getStationId());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tOperatorStationId: ").append("(" + operatorStationId + ") ");
    try {
      sb.append(getOperatorStationId());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tExaminationTypeSc: ").append("(" + examinationTypeSc + ") ");
    try {
      sb.append(getExaminationTypeSc());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tParameterSc: ").append("(" + parameterSc + ") ");
    try {
      sb.append(getParameterSc());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tMeasurementPointNumber: ").append("(" + measurementPointNumber + ") ");
    try {
      sb.append(getMeasurementPointNumber());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tWithResultsAfter: ").append("(" + withResultsAfter + ") ");
    try {
      sb.append(getWithResultsAfter());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tWithResultsCreatedAfter: ").append("(" + withResultsCreatedAfter + ") ");
    try {
      sb.append(getWithResultsCreatedAfter());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tFrom: ").append("(" + from + ") ");
    try {
      sb.append(getFrom());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tTo: ").append("(" + to + ") ");
    try {
      sb.append(getTo());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");
    sb.append("\tCreatedAfter: ").append("(" + createdAfter + ") ");
    try {
      sb.append(getCreatedAfter());
    } catch (Exception ex) {
      sb.append(" N/A due to invalid params");
    }
    sb.append("\n");

    return sb.toString();
  }

  public String getVandahDmpApiUrl() {
    return vandahDmpApiUrl;
  }

  public String getStationId() {
    return stationId;
  }

  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  public String getOperatorStationId() {
    return operatorStationId;
  }

  public boolean isEnableTest() {
    return enableTest;
  }
}
