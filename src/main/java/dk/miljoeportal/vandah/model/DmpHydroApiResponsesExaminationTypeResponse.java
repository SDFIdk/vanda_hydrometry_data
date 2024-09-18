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
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
/**
 * DmpHydroApiResponsesExaminationTypeResponse
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-18T14:15:50.310248991+02:00[Europe/Copenhagen]")

public class DmpHydroApiResponsesExaminationTypeResponse {
  @JsonProperty("examinationTypeSc")
  private Integer examinationTypeSc = null;

  @JsonProperty("examinationTypeName")
  private String examinationTypeName = null;

  @JsonProperty("measurementPointTypeSc")
  private Integer measurementPointTypeSc = null;

  @JsonProperty("parameterSc")
  private Integer parameterSc = null;

  @JsonProperty("unitSc")
  private Integer unitSc = null;

  @JsonProperty("min")
  private Double min = null;

  @JsonProperty("max")
  private Double max = null;

  @JsonProperty("measurementIntervalMin")
  private Integer measurementIntervalMin = null;

  @JsonProperty("decimals")
  private Integer decimals = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("reasonCodesSc")
  private List<Integer> reasonCodesSc = null;

  public DmpHydroApiResponsesExaminationTypeResponse examinationTypeSc(Integer examinationTypeSc) {
    this.examinationTypeSc = examinationTypeSc;
    return this;
  }

   /**
   * Examination type stancode
   * @return examinationTypeSc
  **/
  @Schema(description = "Examination type stancode")
  public Integer getExaminationTypeSc() {
    return examinationTypeSc;
  }

  public void setExaminationTypeSc(Integer examinationTypeSc) {
    this.examinationTypeSc = examinationTypeSc;
  }

  public DmpHydroApiResponsesExaminationTypeResponse examinationTypeName(String examinationTypeName) {
    this.examinationTypeName = examinationTypeName;
    return this;
  }

   /**
   * Examination type name
   * @return examinationTypeName
  **/
  @Schema(description = "Examination type name")
  public String getExaminationTypeName() {
    return examinationTypeName;
  }

  public void setExaminationTypeName(String examinationTypeName) {
    this.examinationTypeName = examinationTypeName;
  }

  public DmpHydroApiResponsesExaminationTypeResponse measurementPointTypeSc(Integer measurementPointTypeSc) {
    this.measurementPointTypeSc = measurementPointTypeSc;
    return this;
  }

   /**
   * Measurement point type stancode, a subset of stancode list 1002
   * @return measurementPointTypeSc
  **/
  @Schema(description = "Measurement point type stancode, a subset of stancode list 1002")
  public Integer getMeasurementPointTypeSc() {
    return measurementPointTypeSc;
  }

  public void setMeasurementPointTypeSc(Integer measurementPointTypeSc) {
    this.measurementPointTypeSc = measurementPointTypeSc;
  }

  public DmpHydroApiResponsesExaminationTypeResponse parameterSc(Integer parameterSc) {
    this.parameterSc = parameterSc;
    return this;
  }

   /**
   * Parameter stancode, a subset of stancode list 1008
   * @return parameterSc
  **/
  @Schema(description = "Parameter stancode, a subset of stancode list 1008")
  public Integer getParameterSc() {
    return parameterSc;
  }

  public void setParameterSc(Integer parameterSc) {
    this.parameterSc = parameterSc;
  }

  public DmpHydroApiResponsesExaminationTypeResponse unitSc(Integer unitSc) {
    this.unitSc = unitSc;
    return this;
  }

   /**
   * Unit stancode, a subset of stancode list 1009
   * @return unitSc
  **/
  @Schema(description = "Unit stancode, a subset of stancode list 1009")
  public Integer getUnitSc() {
    return unitSc;
  }

  public void setUnitSc(Integer unitSc) {
    this.unitSc = unitSc;
  }

  public DmpHydroApiResponsesExaminationTypeResponse min(Double min) {
    this.min = min;
    return this;
  }

   /**
   * Min value of measurements under the examination type
   * @return min
  **/
  @Schema(description = "Min value of measurements under the examination type")
  public Double getMin() {
    return min;
  }

  public void setMin(Double min) {
    this.min = min;
  }

  public DmpHydroApiResponsesExaminationTypeResponse max(Double max) {
    this.max = max;
    return this;
  }

   /**
   * Max value of measurements under the examination type
   * @return max
  **/
  @Schema(description = "Max value of measurements under the examination type")
  public Double getMax() {
    return max;
  }

  public void setMax(Double max) {
    this.max = max;
  }

  public DmpHydroApiResponsesExaminationTypeResponse measurementIntervalMin(Integer measurementIntervalMin) {
    this.measurementIntervalMin = measurementIntervalMin;
    return this;
  }

   /**
   * The minimum interval between measurement in minutes
   * @return measurementIntervalMin
  **/
  @Schema(description = "The minimum interval between measurement in minutes")
  public Integer getMeasurementIntervalMin() {
    return measurementIntervalMin;
  }

  public void setMeasurementIntervalMin(Integer measurementIntervalMin) {
    this.measurementIntervalMin = measurementIntervalMin;
  }

  public DmpHydroApiResponsesExaminationTypeResponse decimals(Integer decimals) {
    this.decimals = decimals;
    return this;
  }

   /**
   * Number of decimals rounded for the measurements values
   * @return decimals
  **/
  @Schema(description = "Number of decimals rounded for the measurements values")
  public Integer getDecimals() {
    return decimals;
  }

  public void setDecimals(Integer decimals) {
    this.decimals = decimals;
  }

  public DmpHydroApiResponsesExaminationTypeResponse description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description for the examination type
   * @return description
  **/
  @Schema(description = "Description for the examination type")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DmpHydroApiResponsesExaminationTypeResponse reasonCodesSc(List<Integer> reasonCodesSc) {
    this.reasonCodesSc = reasonCodesSc;
    return this;
  }

  public DmpHydroApiResponsesExaminationTypeResponse addReasonCodesScItem(Integer reasonCodesScItem) {
    if (this.reasonCodesSc == null) {
      this.reasonCodesSc = new ArrayList<Integer>();
    }
    this.reasonCodesSc.add(reasonCodesScItem);
    return this;
  }

   /**
   * List of reason codes stancode, a subset of stancode list 1188
   * @return reasonCodesSc
  **/
  @Schema(description = "List of reason codes stancode, a subset of stancode list 1188")
  public List<Integer> getReasonCodesSc() {
    return reasonCodesSc;
  }

  public void setReasonCodesSc(List<Integer> reasonCodesSc) {
    this.reasonCodesSc = reasonCodesSc;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiResponsesExaminationTypeResponse dmpHydroApiResponsesExaminationTypeResponse = (DmpHydroApiResponsesExaminationTypeResponse) o;
    return Objects.equals(this.examinationTypeSc, dmpHydroApiResponsesExaminationTypeResponse.examinationTypeSc) &&
        Objects.equals(this.examinationTypeName, dmpHydroApiResponsesExaminationTypeResponse.examinationTypeName) &&
        Objects.equals(this.measurementPointTypeSc, dmpHydroApiResponsesExaminationTypeResponse.measurementPointTypeSc) &&
        Objects.equals(this.parameterSc, dmpHydroApiResponsesExaminationTypeResponse.parameterSc) &&
        Objects.equals(this.unitSc, dmpHydroApiResponsesExaminationTypeResponse.unitSc) &&
        Objects.equals(this.min, dmpHydroApiResponsesExaminationTypeResponse.min) &&
        Objects.equals(this.max, dmpHydroApiResponsesExaminationTypeResponse.max) &&
        Objects.equals(this.measurementIntervalMin, dmpHydroApiResponsesExaminationTypeResponse.measurementIntervalMin) &&
        Objects.equals(this.decimals, dmpHydroApiResponsesExaminationTypeResponse.decimals) &&
        Objects.equals(this.description, dmpHydroApiResponsesExaminationTypeResponse.description) &&
        Objects.equals(this.reasonCodesSc, dmpHydroApiResponsesExaminationTypeResponse.reasonCodesSc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(examinationTypeSc, examinationTypeName, measurementPointTypeSc, parameterSc, unitSc, min, max, measurementIntervalMin, decimals, description, reasonCodesSc);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiResponsesExaminationTypeResponse {\n");
    
    sb.append("    examinationTypeSc: ").append(toIndentedString(examinationTypeSc)).append("\n");
    sb.append("    examinationTypeName: ").append(toIndentedString(examinationTypeName)).append("\n");
    sb.append("    measurementPointTypeSc: ").append(toIndentedString(measurementPointTypeSc)).append("\n");
    sb.append("    parameterSc: ").append(toIndentedString(parameterSc)).append("\n");
    sb.append("    unitSc: ").append(toIndentedString(unitSc)).append("\n");
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
    sb.append("    measurementIntervalMin: ").append(toIndentedString(measurementIntervalMin)).append("\n");
    sb.append("    decimals: ").append(toIndentedString(decimals)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    reasonCodesSc: ").append(toIndentedString(reasonCodesSc)).append("\n");
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
