/*
 * Dmp.Hydro.Api
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package dk.miljoeportal.vandah.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DmpHydroApiResponsesMeasurementResultResponse
 */
public class DmpHydroApiResponsesMeasurementResultResponse {
  @JsonProperty("stationId")
  private String stationId = null;

  @JsonProperty("operatorStationId")
  private String operatorStationId = null;

  @JsonProperty("stationName")
  private String stationName = null;

  @JsonProperty("results")
  private List<DmpHydroApiResponsesResultResponse> results = null;

  public DmpHydroApiResponsesMeasurementResultResponse stationId(String stationId) {
    this.stationId = stationId;
    return this;
  }

  /**
   * A 8-digit station id
   *
   * @return stationId
   **/
  public String getStationId() {
    return stationId;
  }

  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  public DmpHydroApiResponsesMeasurementResultResponse operatorStationId(String operatorStationId) {
    this.operatorStationId = operatorStationId;
    return this;
  }

  /**
   * Operator station id
   *
   * @return operatorStationId
   **/
  public String getOperatorStationId() {
    return operatorStationId;
  }

  public void setOperatorStationId(String operatorStationId) {
    this.operatorStationId = operatorStationId;
  }

  public DmpHydroApiResponsesMeasurementResultResponse stationName(String stationName) {
    this.stationName = stationName;
    return this;
  }

  /**
   * Station name
   *
   * @return stationName
   **/
  public String getStationName() {
    return stationName;
  }

  public void setStationName(String stationName) {
    this.stationName = stationName;
  }

  public DmpHydroApiResponsesMeasurementResultResponse results(
      List<DmpHydroApiResponsesResultResponse> results) {
    this.results = results;
    return this;
  }

  public DmpHydroApiResponsesMeasurementResultResponse addResultsItem(
      DmpHydroApiResponsesResultResponse resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<DmpHydroApiResponsesResultResponse>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * Measurement results
   *
   * @return results
   **/
  public List<DmpHydroApiResponsesResultResponse> getResults() {
    return results;
  }

  public void setResults(List<DmpHydroApiResponsesResultResponse> results) {
    this.results = results;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiResponsesMeasurementResultResponse dmpHydroApiResponsesMeasurementResultResponse =
        (DmpHydroApiResponsesMeasurementResultResponse) o;
    return
        Objects.equals(this.stationId, dmpHydroApiResponsesMeasurementResultResponse.stationId) &&
            Objects.equals(this.operatorStationId,
                dmpHydroApiResponsesMeasurementResultResponse.operatorStationId) &&
            Objects.equals(this.stationName,
                dmpHydroApiResponsesMeasurementResultResponse.stationName) &&
            Objects.equals(this.results, dmpHydroApiResponsesMeasurementResultResponse.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stationId, operatorStationId, stationName, results);
  }


  @Override
  public String toString() {

    String sb = "class DmpHydroApiResponsesMeasurementResultResponse {\n" +
        "    stationId: " + toIndentedString(stationId) + "\n" +
        "    operatorStationId: " + toIndentedString(operatorStationId) + "\n" +
        "    stationName: " + toIndentedString(stationName) + "\n" +
        "    results: " + toIndentedString(results) + "\n" +
        "}";
    return sb;
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
