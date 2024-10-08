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

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesLocationResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPoint;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * DmpHydroApiResponsesStationResponse
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-27T10:11:34.799092956+02:00[Europe/Copenhagen]")

public class DmpHydroApiResponsesStationResponse {
  @JsonProperty("stationUid")
  private UUID stationUid = null;

  @JsonProperty("stationId")
  private String stationId = null;

  @JsonProperty("operatorStationId")
  private String operatorStationId = null;

  @JsonProperty("oldStationNumber")
  private String oldStationNumber = null;

  @JsonProperty("locationType")
  private String locationType = null;

  @JsonProperty("locationTypeSc")
  private Integer locationTypeSc = null;

  @JsonProperty("stationOwnerCvr")
  private String stationOwnerCvr = null;

  @JsonProperty("stationOwnerName")
  private String stationOwnerName = null;

  @JsonProperty("operatorCvr")
  private String operatorCvr = null;

  @JsonProperty("operatorName")
  private String operatorName = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("dguNumber")
  private String dguNumber = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("loggerId")
  private String loggerId = null;

  @JsonProperty("location")
  private DmpHydroApiResponsesLocationResponse location = null;

  @JsonProperty("measurementPoints")
  private List<DmpHydroApiResponsesStationResponseMeasurementPoint> measurementPoints = null;

  public DmpHydroApiResponsesStationResponse stationUid(UUID stationUid) {
    this.stationUid = stationUid;
    return this;
  }

   /**
   * Vanda station GUID id
   * @return stationUid
  **/
  @Schema(description = "Vanda station GUID id")
  public UUID getStationUid() {
    return stationUid;
  }

  public void setStationUid(UUID stationUid) {
    this.stationUid = stationUid;
  }

  public DmpHydroApiResponsesStationResponse stationId(String stationId) {
    this.stationId = stationId;
    return this;
  }

   /**
   * A 8-digits station id
   * @return stationId
  **/
  @Schema(description = "A 8-digits station id")
  public String getStationId() {
    return stationId;
  }

  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  public DmpHydroApiResponsesStationResponse operatorStationId(String operatorStationId) {
    this.operatorStationId = operatorStationId;
    return this;
  }

   /**
   * Operator station id
   * @return operatorStationId
  **/
  @Schema(description = "Operator station id")
  public String getOperatorStationId() {
    return operatorStationId;
  }

  public void setOperatorStationId(String operatorStationId) {
    this.operatorStationId = operatorStationId;
  }

  public DmpHydroApiResponsesStationResponse oldStationNumber(String oldStationNumber) {
    this.oldStationNumber = oldStationNumber;
    return this;
  }

   /**
   * Old station number
   * @return oldStationNumber
  **/
  @Schema(description = "Old station number")
  public String getOldStationNumber() {
    return oldStationNumber;
  }

  public void setOldStationNumber(String oldStationNumber) {
    this.oldStationNumber = oldStationNumber;
  }

  public DmpHydroApiResponsesStationResponse locationType(String locationType) {
    this.locationType = locationType;
    return this;
  }

   /**
   * Location type name
   * @return locationType
  **/
  @Schema(description = "Location type name")
  public String getLocationType() {
    return locationType;
  }

  public void setLocationType(String locationType) {
    this.locationType = locationType;
  }

  public DmpHydroApiResponsesStationResponse locationTypeSc(Integer locationTypeSc) {
    this.locationTypeSc = locationTypeSc;
    return this;
  }

   /**
   * Location type stancode
   * @return locationTypeSc
  **/
  @Schema(description = "Location type stancode")
  public Integer getLocationTypeSc() {
    return locationTypeSc;
  }

  public void setLocationTypeSc(Integer locationTypeSc) {
    this.locationTypeSc = locationTypeSc;
  }

  public DmpHydroApiResponsesStationResponse stationOwnerCvr(String stationOwnerCvr) {
    this.stationOwnerCvr = stationOwnerCvr;
    return this;
  }

   /**
   * Station owner cvr number
   * @return stationOwnerCvr
  **/
  @Schema(description = "Station owner cvr number")
  public String getStationOwnerCvr() {
    return stationOwnerCvr;
  }

  public void setStationOwnerCvr(String stationOwnerCvr) {
    this.stationOwnerCvr = stationOwnerCvr;
  }

  public DmpHydroApiResponsesStationResponse stationOwnerName(String stationOwnerName) {
    this.stationOwnerName = stationOwnerName;
    return this;
  }

   /**
   * Station owner name
   * @return stationOwnerName
  **/
  @Schema(description = "Station owner name")
  public String getStationOwnerName() {
    return stationOwnerName;
  }

  public void setStationOwnerName(String stationOwnerName) {
    this.stationOwnerName = stationOwnerName;
  }

  public DmpHydroApiResponsesStationResponse operatorCvr(String operatorCvr) {
    this.operatorCvr = operatorCvr;
    return this;
  }

   /**
   * Operator cvr number
   * @return operatorCvr
  **/
  @Schema(description = "Operator cvr number")
  public String getOperatorCvr() {
    return operatorCvr;
  }

  public void setOperatorCvr(String operatorCvr) {
    this.operatorCvr = operatorCvr;
  }

  public DmpHydroApiResponsesStationResponse operatorName(String operatorName) {
    this.operatorName = operatorName;
    return this;
  }

   /**
   * Operator name
   * @return operatorName
  **/
  @Schema(description = "Operator name")
  public String getOperatorName() {
    return operatorName;
  }

  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  public DmpHydroApiResponsesStationResponse name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Station name
   * @return name
  **/
  @Schema(description = "Station name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DmpHydroApiResponsesStationResponse dguNumber(String dguNumber) {
    this.dguNumber = dguNumber;
    return this;
  }

   /**
   * DGU number of Groundwater station
   * @return dguNumber
  **/
  @Schema(description = "DGU number of Groundwater station")
  public String getDguNumber() {
    return dguNumber;
  }

  public void setDguNumber(String dguNumber) {
    this.dguNumber = dguNumber;
  }

  public DmpHydroApiResponsesStationResponse description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Station description
   * @return description
  **/
  @Schema(description = "Station description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DmpHydroApiResponsesStationResponse loggerId(String loggerId) {
    this.loggerId = loggerId;
    return this;
  }

   /**
   * Logger id
   * @return loggerId
  **/
  @Schema(description = "Logger id")
  public String getLoggerId() {
    return loggerId;
  }

  public void setLoggerId(String loggerId) {
    this.loggerId = loggerId;
  }

  public DmpHydroApiResponsesStationResponse location(DmpHydroApiResponsesLocationResponse location) {
    this.location = location;
    return this;
  }

   /**
   * Get location
   * @return location
  **/
  @Schema(description = "")
  public DmpHydroApiResponsesLocationResponse getLocation() {
    return location;
  }

  public void setLocation(DmpHydroApiResponsesLocationResponse location) {
    this.location = location;
  }

  public DmpHydroApiResponsesStationResponse measurementPoints(List<DmpHydroApiResponsesStationResponseMeasurementPoint> measurementPoints) {
    this.measurementPoints = measurementPoints;
    return this;
  }

  public DmpHydroApiResponsesStationResponse addMeasurementPointsItem(DmpHydroApiResponsesStationResponseMeasurementPoint measurementPointsItem) {
    if (this.measurementPoints == null) {
      this.measurementPoints = new ArrayList<DmpHydroApiResponsesStationResponseMeasurementPoint>();
    }
    this.measurementPoints.add(measurementPointsItem);
    return this;
  }

   /**
   * A list of measurement points data in station
   * @return measurementPoints
  **/
  @Schema(description = "A list of measurement points data in station")
  public List<DmpHydroApiResponsesStationResponseMeasurementPoint> getMeasurementPoints() {
    return measurementPoints;
  }

  public void setMeasurementPoints(List<DmpHydroApiResponsesStationResponseMeasurementPoint> measurementPoints) {
    this.measurementPoints = measurementPoints;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiResponsesStationResponse dmpHydroApiResponsesStationResponse = (DmpHydroApiResponsesStationResponse) o;
    return Objects.equals(this.stationUid, dmpHydroApiResponsesStationResponse.stationUid) &&
        Objects.equals(this.stationId, dmpHydroApiResponsesStationResponse.stationId) &&
        Objects.equals(this.operatorStationId, dmpHydroApiResponsesStationResponse.operatorStationId) &&
        Objects.equals(this.oldStationNumber, dmpHydroApiResponsesStationResponse.oldStationNumber) &&
        Objects.equals(this.locationType, dmpHydroApiResponsesStationResponse.locationType) &&
        Objects.equals(this.locationTypeSc, dmpHydroApiResponsesStationResponse.locationTypeSc) &&
        Objects.equals(this.stationOwnerCvr, dmpHydroApiResponsesStationResponse.stationOwnerCvr) &&
        Objects.equals(this.stationOwnerName, dmpHydroApiResponsesStationResponse.stationOwnerName) &&
        Objects.equals(this.operatorCvr, dmpHydroApiResponsesStationResponse.operatorCvr) &&
        Objects.equals(this.operatorName, dmpHydroApiResponsesStationResponse.operatorName) &&
        Objects.equals(this.name, dmpHydroApiResponsesStationResponse.name) &&
        Objects.equals(this.dguNumber, dmpHydroApiResponsesStationResponse.dguNumber) &&
        Objects.equals(this.description, dmpHydroApiResponsesStationResponse.description) &&
        Objects.equals(this.loggerId, dmpHydroApiResponsesStationResponse.loggerId) &&
        Objects.equals(this.location, dmpHydroApiResponsesStationResponse.location) &&
        Objects.equals(this.measurementPoints, dmpHydroApiResponsesStationResponse.measurementPoints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stationUid, stationId, operatorStationId, oldStationNumber, locationType, locationTypeSc, stationOwnerCvr, stationOwnerName, operatorCvr, operatorName, name, dguNumber, description, loggerId, location, measurementPoints);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiResponsesStationResponse {\n");
    
    sb.append("    stationUid: ").append(toIndentedString(stationUid)).append("\n");
    sb.append("    stationId: ").append(toIndentedString(stationId)).append("\n");
    sb.append("    operatorStationId: ").append(toIndentedString(operatorStationId)).append("\n");
    sb.append("    oldStationNumber: ").append(toIndentedString(oldStationNumber)).append("\n");
    sb.append("    locationType: ").append(toIndentedString(locationType)).append("\n");
    sb.append("    locationTypeSc: ").append(toIndentedString(locationTypeSc)).append("\n");
    sb.append("    stationOwnerCvr: ").append(toIndentedString(stationOwnerCvr)).append("\n");
    sb.append("    stationOwnerName: ").append(toIndentedString(stationOwnerName)).append("\n");
    sb.append("    operatorCvr: ").append(toIndentedString(operatorCvr)).append("\n");
    sb.append("    operatorName: ").append(toIndentedString(operatorName)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    dguNumber: ").append(toIndentedString(dguNumber)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    loggerId: ").append(toIndentedString(loggerId)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    measurementPoints: ").append(toIndentedString(measurementPoints)).append("\n");
    sb.append("}");
    return sb.toString();
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
