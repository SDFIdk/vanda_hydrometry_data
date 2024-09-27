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
import java.util.ArrayList;
import java.util.Date;
import java.time.OffsetDateTime;
import java.util.List;
/**
 * DmpHydroApiRequestsDeleteSpecificMeasurementsRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-27T10:11:34.799092956+02:00[Europe/Copenhagen]")

public class DmpHydroApiRequestsDeleteSpecificMeasurementsRequest {
  @JsonProperty("station")
  private DmpHydroApiRequestsStationIdentifier station = null;

  @JsonProperty("measurementPointNumber")
  private Integer measurementPointNumber = null;

  @JsonProperty("examinationTypeSc")
  private Integer examinationTypeSc = null;

  @JsonProperty("measurementDateTimes")
  private List<OffsetDateTime> measurementDateTimes = null;

  public DmpHydroApiRequestsDeleteSpecificMeasurementsRequest station(DmpHydroApiRequestsStationIdentifier station) {
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

  public DmpHydroApiRequestsDeleteSpecificMeasurementsRequest measurementPointNumber(Integer measurementPointNumber) {
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

  public DmpHydroApiRequestsDeleteSpecificMeasurementsRequest examinationTypeSc(Integer examinationTypeSc) {
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

  public DmpHydroApiRequestsDeleteSpecificMeasurementsRequest measurementDateTimes(List<OffsetDateTime> measurementDateTimes) {
    this.measurementDateTimes = measurementDateTimes;
    return this;
  }

  public DmpHydroApiRequestsDeleteSpecificMeasurementsRequest addMeasurementDateTimesItem(OffsetDateTime measurementDateTimesItem) {
    if (this.measurementDateTimes == null) {
      this.measurementDateTimes = new ArrayList<OffsetDateTime>();
    }
    this.measurementDateTimes.add(measurementDateTimesItem);
    return this;
  }

   /**
   * A list of measurement dates.Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;.
   * @return measurementDateTimes
  **/
  @Schema(description = "A list of measurement dates.Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example '2023-09-21T14:34Z'.")
  public List<OffsetDateTime> getMeasurementDateTimes() {
    return measurementDateTimes;
  }

  public void setMeasurementDateTimes(List<OffsetDateTime> measurementDateTimes) {
    this.measurementDateTimes = measurementDateTimes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiRequestsDeleteSpecificMeasurementsRequest dmpHydroApiRequestsDeleteSpecificMeasurementsRequest = (DmpHydroApiRequestsDeleteSpecificMeasurementsRequest) o;
    return Objects.equals(this.station, dmpHydroApiRequestsDeleteSpecificMeasurementsRequest.station) &&
        Objects.equals(this.measurementPointNumber, dmpHydroApiRequestsDeleteSpecificMeasurementsRequest.measurementPointNumber) &&
        Objects.equals(this.examinationTypeSc, dmpHydroApiRequestsDeleteSpecificMeasurementsRequest.examinationTypeSc) &&
        Objects.equals(this.measurementDateTimes, dmpHydroApiRequestsDeleteSpecificMeasurementsRequest.measurementDateTimes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(station, measurementPointNumber, examinationTypeSc, measurementDateTimes);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiRequestsDeleteSpecificMeasurementsRequest {\n");
    
    sb.append("    station: ").append(toIndentedString(station)).append("\n");
    sb.append("    measurementPointNumber: ").append(toIndentedString(measurementPointNumber)).append("\n");
    sb.append("    examinationTypeSc: ").append(toIndentedString(examinationTypeSc)).append("\n");
    sb.append("    measurementDateTimes: ").append(toIndentedString(measurementDateTimes)).append("\n");
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
