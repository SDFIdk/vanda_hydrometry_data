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
import dk.miljoeportal.vandah.model.DmpHydroApiRequestsMeasurementRequest;
import dk.miljoeportal.vandah.model.DmpHydroApiRequestsStationIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
/**
 * DmpHydroApiRequestsCreateMeasurementsRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-18T14:15:50.310248991+02:00[Europe/Copenhagen]")

public class DmpHydroApiRequestsCreateMeasurementsRequest {
  @JsonProperty("station")
  private DmpHydroApiRequestsStationIdentifier station = null;

  @JsonProperty("measurementPointNumber")
  private Integer measurementPointNumber = null;

  @JsonProperty("examinationTypeSc")
  private Integer examinationTypeSc = null;

  @JsonProperty("loggerId")
  private String loggerId = null;

  @JsonProperty("parameterSc")
  private Integer parameterSc = null;

  @JsonProperty("unitSc")
  private Integer unitSc = null;

  @JsonProperty("transactionBased")
  private Boolean transactionBased = null;

  @JsonProperty("measurements")
  private List<DmpHydroApiRequestsMeasurementRequest> measurements = new ArrayList<DmpHydroApiRequestsMeasurementRequest>();

  public DmpHydroApiRequestsCreateMeasurementsRequest station(DmpHydroApiRequestsStationIdentifier station) {
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

  public DmpHydroApiRequestsCreateMeasurementsRequest measurementPointNumber(Integer measurementPointNumber) {
    this.measurementPointNumber = measurementPointNumber;
    return this;
  }

   /**
   * Measurement point number in the station
   * @return measurementPointNumber
  **/
  @Schema(required = true, description = "Measurement point number in the station")
  public Integer getMeasurementPointNumber() {
    return measurementPointNumber;
  }

  public void setMeasurementPointNumber(Integer measurementPointNumber) {
    this.measurementPointNumber = measurementPointNumber;
  }

  public DmpHydroApiRequestsCreateMeasurementsRequest examinationTypeSc(Integer examinationTypeSc) {
    this.examinationTypeSc = examinationTypeSc;
    return this;
  }

   /**
   * Examination type stancode, a subset of stancode 1101
   * @return examinationTypeSc
  **/
  @Schema(required = true, description = "Examination type stancode, a subset of stancode 1101")
  public Integer getExaminationTypeSc() {
    return examinationTypeSc;
  }

  public void setExaminationTypeSc(Integer examinationTypeSc) {
    this.examinationTypeSc = examinationTypeSc;
  }

  public DmpHydroApiRequestsCreateMeasurementsRequest loggerId(String loggerId) {
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

  public DmpHydroApiRequestsCreateMeasurementsRequest parameterSc(Integer parameterSc) {
    this.parameterSc = parameterSc;
    return this;
  }

   /**
   * Parameter stancode, a subset of stancode list 1008
   * @return parameterSc
  **/
  @Schema(required = true, description = "Parameter stancode, a subset of stancode list 1008")
  public Integer getParameterSc() {
    return parameterSc;
  }

  public void setParameterSc(Integer parameterSc) {
    this.parameterSc = parameterSc;
  }

  public DmpHydroApiRequestsCreateMeasurementsRequest unitSc(Integer unitSc) {
    this.unitSc = unitSc;
    return this;
  }

   /**
   * Unit stancode, a subset of stancode list 1009
   * @return unitSc
  **/
  @Schema(required = true, description = "Unit stancode, a subset of stancode list 1009")
  public Integer getUnitSc() {
    return unitSc;
  }

  public void setUnitSc(Integer unitSc) {
    this.unitSc = unitSc;
  }

  public DmpHydroApiRequestsCreateMeasurementsRequest transactionBased(Boolean transactionBased) {
    this.transactionBased = transactionBased;
    return this;
  }

   /**
   * True: The batch is treated as a transaction. If one or more fails, no data is persisted in the database. False: Measurements are treated individually. Some might fail while others are persisted in the database.
   * @return transactionBased
  **/
  @Schema(description = "True: The batch is treated as a transaction. If one or more fails, no data is persisted in the database. False: Measurements are treated individually. Some might fail while others are persisted in the database.")
  public Boolean isTransactionBased() {
    return transactionBased;
  }

  public void setTransactionBased(Boolean transactionBased) {
    this.transactionBased = transactionBased;
  }

  public DmpHydroApiRequestsCreateMeasurementsRequest measurements(List<DmpHydroApiRequestsMeasurementRequest> measurements) {
    this.measurements = measurements;
    return this;
  }

  public DmpHydroApiRequestsCreateMeasurementsRequest addMeasurementsItem(DmpHydroApiRequestsMeasurementRequest measurementsItem) {
    this.measurements.add(measurementsItem);
    return this;
  }

   /**
   * A list of measurements on the measurement point
   * @return measurements
  **/
  @Schema(required = true, description = "A list of measurements on the measurement point")
  public List<DmpHydroApiRequestsMeasurementRequest> getMeasurements() {
    return measurements;
  }

  public void setMeasurements(List<DmpHydroApiRequestsMeasurementRequest> measurements) {
    this.measurements = measurements;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiRequestsCreateMeasurementsRequest dmpHydroApiRequestsCreateMeasurementsRequest = (DmpHydroApiRequestsCreateMeasurementsRequest) o;
    return Objects.equals(this.station, dmpHydroApiRequestsCreateMeasurementsRequest.station) &&
        Objects.equals(this.measurementPointNumber, dmpHydroApiRequestsCreateMeasurementsRequest.measurementPointNumber) &&
        Objects.equals(this.examinationTypeSc, dmpHydroApiRequestsCreateMeasurementsRequest.examinationTypeSc) &&
        Objects.equals(this.loggerId, dmpHydroApiRequestsCreateMeasurementsRequest.loggerId) &&
        Objects.equals(this.parameterSc, dmpHydroApiRequestsCreateMeasurementsRequest.parameterSc) &&
        Objects.equals(this.unitSc, dmpHydroApiRequestsCreateMeasurementsRequest.unitSc) &&
        Objects.equals(this.transactionBased, dmpHydroApiRequestsCreateMeasurementsRequest.transactionBased) &&
        Objects.equals(this.measurements, dmpHydroApiRequestsCreateMeasurementsRequest.measurements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(station, measurementPointNumber, examinationTypeSc, loggerId, parameterSc, unitSc, transactionBased, measurements);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiRequestsCreateMeasurementsRequest {\n");
    
    sb.append("    station: ").append(toIndentedString(station)).append("\n");
    sb.append("    measurementPointNumber: ").append(toIndentedString(measurementPointNumber)).append("\n");
    sb.append("    examinationTypeSc: ").append(toIndentedString(examinationTypeSc)).append("\n");
    sb.append("    loggerId: ").append(toIndentedString(loggerId)).append("\n");
    sb.append("    parameterSc: ").append(toIndentedString(parameterSc)).append("\n");
    sb.append("    unitSc: ").append(toIndentedString(unitSc)).append("\n");
    sb.append("    transactionBased: ").append(toIndentedString(transactionBased)).append("\n");
    sb.append("    measurements: ").append(toIndentedString(measurements)).append("\n");
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