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
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponseMeasurementPointExamination;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
/**
 * DmpHydroApiResponsesStationResponseMeasurementPoint
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-18T14:15:50.310248991+02:00[Europe/Copenhagen]")

public class DmpHydroApiResponsesStationResponseMeasurementPoint {
  @JsonProperty("number")
  private Integer number = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("measurementPointType")
  private String measurementPointType = null;

  @JsonProperty("measurementPointTypeSc")
  private Integer measurementPointTypeSc = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("location")
  private DmpHydroApiResponsesLocationResponse location = null;

  @JsonProperty("intakeNumber")
  private Integer intakeNumber = null;

  @JsonProperty("examinations")
  private List<DmpHydroApiResponsesStationResponseMeasurementPointExamination> examinations = null;

  public DmpHydroApiResponsesStationResponseMeasurementPoint number(Integer number) {
    this.number = number;
    return this;
  }

   /**
   * Measurement point number in a station
   * @return number
  **/
  @Schema(description = "Measurement point number in a station")
  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public DmpHydroApiResponsesStationResponseMeasurementPoint name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Measurement point name
   * @return name
  **/
  @Schema(description = "Measurement point name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DmpHydroApiResponsesStationResponseMeasurementPoint measurementPointType(String measurementPointType) {
    this.measurementPointType = measurementPointType;
    return this;
  }

   /**
   * Measurement point type name
   * @return measurementPointType
  **/
  @Schema(description = "Measurement point type name")
  public String getMeasurementPointType() {
    return measurementPointType;
  }

  public void setMeasurementPointType(String measurementPointType) {
    this.measurementPointType = measurementPointType;
  }

  public DmpHydroApiResponsesStationResponseMeasurementPoint measurementPointTypeSc(Integer measurementPointTypeSc) {
    this.measurementPointTypeSc = measurementPointTypeSc;
    return this;
  }

   /**
   * Measurement point type stancode
   * @return measurementPointTypeSc
  **/
  @Schema(description = "Measurement point type stancode")
  public Integer getMeasurementPointTypeSc() {
    return measurementPointTypeSc;
  }

  public void setMeasurementPointTypeSc(Integer measurementPointTypeSc) {
    this.measurementPointTypeSc = measurementPointTypeSc;
  }

  public DmpHydroApiResponsesStationResponseMeasurementPoint description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Measurement point description
   * @return description
  **/
  @Schema(description = "Measurement point description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DmpHydroApiResponsesStationResponseMeasurementPoint location(DmpHydroApiResponsesLocationResponse location) {
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

  public DmpHydroApiResponsesStationResponseMeasurementPoint intakeNumber(Integer intakeNumber) {
    this.intakeNumber = intakeNumber;
    return this;
  }

   /**
   * Intake number of Grundvand station
   * @return intakeNumber
  **/
  @Schema(description = "Intake number of Grundvand station")
  public Integer getIntakeNumber() {
    return intakeNumber;
  }

  public void setIntakeNumber(Integer intakeNumber) {
    this.intakeNumber = intakeNumber;
  }

  public DmpHydroApiResponsesStationResponseMeasurementPoint examinations(List<DmpHydroApiResponsesStationResponseMeasurementPointExamination> examinations) {
    this.examinations = examinations;
    return this;
  }

  public DmpHydroApiResponsesStationResponseMeasurementPoint addExaminationsItem(DmpHydroApiResponsesStationResponseMeasurementPointExamination examinationsItem) {
    if (this.examinations == null) {
      this.examinations = new ArrayList<DmpHydroApiResponsesStationResponseMeasurementPointExamination>();
    }
    this.examinations.add(examinationsItem);
    return this;
  }

   /**
   * A list of examination performed on the measurement point
   * @return examinations
  **/
  @Schema(description = "A list of examination performed on the measurement point")
  public List<DmpHydroApiResponsesStationResponseMeasurementPointExamination> getExaminations() {
    return examinations;
  }

  public void setExaminations(List<DmpHydroApiResponsesStationResponseMeasurementPointExamination> examinations) {
    this.examinations = examinations;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiResponsesStationResponseMeasurementPoint dmpHydroApiResponsesStationResponseMeasurementPoint = (DmpHydroApiResponsesStationResponseMeasurementPoint) o;
    return Objects.equals(this.number, dmpHydroApiResponsesStationResponseMeasurementPoint.number) &&
        Objects.equals(this.name, dmpHydroApiResponsesStationResponseMeasurementPoint.name) &&
        Objects.equals(this.measurementPointType, dmpHydroApiResponsesStationResponseMeasurementPoint.measurementPointType) &&
        Objects.equals(this.measurementPointTypeSc, dmpHydroApiResponsesStationResponseMeasurementPoint.measurementPointTypeSc) &&
        Objects.equals(this.description, dmpHydroApiResponsesStationResponseMeasurementPoint.description) &&
        Objects.equals(this.location, dmpHydroApiResponsesStationResponseMeasurementPoint.location) &&
        Objects.equals(this.intakeNumber, dmpHydroApiResponsesStationResponseMeasurementPoint.intakeNumber) &&
        Objects.equals(this.examinations, dmpHydroApiResponsesStationResponseMeasurementPoint.examinations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, name, measurementPointType, measurementPointTypeSc, description, location, intakeNumber, examinations);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiResponsesStationResponseMeasurementPoint {\n");
    
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    measurementPointType: ").append(toIndentedString(measurementPointType)).append("\n");
    sb.append("    measurementPointTypeSc: ").append(toIndentedString(measurementPointTypeSc)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    intakeNumber: ").append(toIndentedString(intakeNumber)).append("\n");
    sb.append("    examinations: ").append(toIndentedString(examinations)).append("\n");
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
