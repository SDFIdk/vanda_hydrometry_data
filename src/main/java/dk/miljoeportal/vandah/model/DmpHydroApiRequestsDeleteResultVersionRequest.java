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
import dk.miljoeportal.vandah.model.DmpHydroApiRequestsStationIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
/**
 * DmpHydroApiRequestsDeleteResultVersionRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-18T14:15:50.310248991+02:00[Europe/Copenhagen]")

public class DmpHydroApiRequestsDeleteResultVersionRequest {
  @JsonProperty("station")
  private DmpHydroApiRequestsStationIdentifier station = null;

  @JsonProperty("measurementPointNumber")
  private Integer measurementPointNumber = null;

  @JsonProperty("examinationTypeSc")
  private Integer examinationTypeSc = null;

  @JsonProperty("measurementDateTime")
  private Date measurementDateTime = null;

  @JsonProperty("changedTimestamp")
  private Date changedTimestamp = null;

  public DmpHydroApiRequestsDeleteResultVersionRequest station(DmpHydroApiRequestsStationIdentifier station) {
    this.station = station;
    return this;
  }

   /**
   * Get station
   * @return station
  **/
  @Schema(required = true, description = "")
  public DmpHydroApiRequestsStationIdentifier getStation() {
    return station;
  }

  public void setStation(DmpHydroApiRequestsStationIdentifier station) {
    this.station = station;
  }

  public DmpHydroApiRequestsDeleteResultVersionRequest measurementPointNumber(Integer measurementPointNumber) {
    this.measurementPointNumber = measurementPointNumber;
    return this;
  }

   /**
   * Measurement point number
   * @return measurementPointNumber
  **/
  @Schema(required = true, description = "Measurement point number")
  public Integer getMeasurementPointNumber() {
    return measurementPointNumber;
  }

  public void setMeasurementPointNumber(Integer measurementPointNumber) {
    this.measurementPointNumber = measurementPointNumber;
  }

  public DmpHydroApiRequestsDeleteResultVersionRequest examinationTypeSc(Integer examinationTypeSc) {
    this.examinationTypeSc = examinationTypeSc;
    return this;
  }

   /**
   * Examination type stancode
   * @return examinationTypeSc
  **/
  @Schema(required = true, description = "Examination type stancode")
  public Integer getExaminationTypeSc() {
    return examinationTypeSc;
  }

  public void setExaminationTypeSc(Integer examinationTypeSc) {
    this.examinationTypeSc = examinationTypeSc;
  }

  public DmpHydroApiRequestsDeleteResultVersionRequest measurementDateTime(Date measurementDateTime) {
    this.measurementDateTime = measurementDateTime;
    return this;
  }

   /**
   * Measurement date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;.
   * @return measurementDateTime
  **/
  @Schema(required = true, description = "Measurement date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.")
  public Date getMeasurementDateTime() {
    return measurementDateTime;
  }

  public void setMeasurementDateTime(Date measurementDateTime) {
    this.measurementDateTime = measurementDateTime;
  }

  public DmpHydroApiRequestsDeleteResultVersionRequest changedTimestamp(Date changedTimestamp) {
    this.changedTimestamp = changedTimestamp;
    return this;
  }

   /**
   * Changed timestamp. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;.
   * @return changedTimestamp
  **/
  @Schema(required = true, description = "Changed timestamp. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.")
  public Date getChangedTimestamp() {
    return changedTimestamp;
  }

  public void setChangedTimestamp(Date changedTimestamp) {
    this.changedTimestamp = changedTimestamp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiRequestsDeleteResultVersionRequest dmpHydroApiRequestsDeleteResultVersionRequest = (DmpHydroApiRequestsDeleteResultVersionRequest) o;
    return Objects.equals(this.station, dmpHydroApiRequestsDeleteResultVersionRequest.station) &&
        Objects.equals(this.measurementPointNumber, dmpHydroApiRequestsDeleteResultVersionRequest.measurementPointNumber) &&
        Objects.equals(this.examinationTypeSc, dmpHydroApiRequestsDeleteResultVersionRequest.examinationTypeSc) &&
        Objects.equals(this.measurementDateTime, dmpHydroApiRequestsDeleteResultVersionRequest.measurementDateTime) &&
        Objects.equals(this.changedTimestamp, dmpHydroApiRequestsDeleteResultVersionRequest.changedTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(station, measurementPointNumber, examinationTypeSc, measurementDateTime, changedTimestamp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiRequestsDeleteResultVersionRequest {\n");
    
    sb.append("    station: ").append(toIndentedString(station)).append("\n");
    sb.append("    measurementPointNumber: ").append(toIndentedString(measurementPointNumber)).append("\n");
    sb.append("    examinationTypeSc: ").append(toIndentedString(examinationTypeSc)).append("\n");
    sb.append("    measurementDateTime: ").append(toIndentedString(measurementDateTime)).append("\n");
    sb.append("    changedTimestamp: ").append(toIndentedString(changedTimestamp)).append("\n");
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