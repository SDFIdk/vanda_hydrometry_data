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
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * DmpHydroApiResponsesResultResponse
 */
public class DmpHydroApiResponsesResultResponse {
  @JsonProperty("measurementPointNumber")
  private Integer measurementPointNumber = null;

  @JsonProperty("measurementPointTypeSc")
  private Integer measurementPointTypeSc = null;

  @JsonProperty("measurementPointType")
  private String measurementPointType = null;

  @JsonProperty("parameterSc")
  private Integer parameterSc = null;

  @JsonProperty("parameter")
  private String parameter = null;

  @JsonProperty("examinationTypeSc")
  private Integer examinationTypeSc = null;

  @JsonProperty("examinationType")
  private String examinationType = null;

  @JsonProperty("measurementDateTime")
  private OffsetDateTime measurementDateTime = null;

  @JsonProperty("result")
  private Double result = null;

  @JsonProperty("resultElevationCorrected")
  private Double resultElevationCorrected = null;

  @JsonProperty("unitSc")
  private Integer unitSc = null;

  @JsonProperty("unit")
  private String unit = null;

  @JsonProperty("loggerId")
  private String loggerId = null;

  @JsonProperty("formulaId")
  private String formulaId = null;

  @JsonProperty("createdTimestamp")
  private OffsetDateTime createdTimestamp = null;

  @JsonProperty("reasonSc")
  private Integer reasonSc = null;

  @JsonProperty("reason")
  private String reason = null;

  @JsonProperty("vegetationIndex")
  private Integer vegetationIndex = null;

  @JsonProperty("refPointText")
  private String refPointText = null;

  @JsonProperty("distToFixPointM")
  private Double distToFixPointM = null;

  public DmpHydroApiResponsesResultResponse measurementPointNumber(Integer measurementPointNumber) {
    this.measurementPointNumber = measurementPointNumber;
    return this;
  }

  /**
   * Measurement point number
   *
   * @return measurementPointNumber
   **/
  public Integer getMeasurementPointNumber() {
    return measurementPointNumber;
  }

  public void setMeasurementPointNumber(Integer measurementPointNumber) {
    this.measurementPointNumber = measurementPointNumber;
  }

  public DmpHydroApiResponsesResultResponse measurementPointTypeSc(Integer measurementPointTypeSc) {
    this.measurementPointTypeSc = measurementPointTypeSc;
    return this;
  }

  /**
   * Measurement point type stancode
   *
   * @return measurementPointTypeSc
   **/
  public Integer getMeasurementPointTypeSc() {
    return measurementPointTypeSc;
  }

  public void setMeasurementPointTypeSc(Integer measurementPointTypeSc) {
    this.measurementPointTypeSc = measurementPointTypeSc;
  }

  public DmpHydroApiResponsesResultResponse measurementPointType(String measurementPointType) {
    this.measurementPointType = measurementPointType;
    return this;
  }

  /**
   * Measurement point type name
   *
   * @return measurementPointType
   **/
  public String getMeasurementPointType() {
    return measurementPointType;
  }

  public void setMeasurementPointType(String measurementPointType) {
    this.measurementPointType = measurementPointType;
  }

  public DmpHydroApiResponsesResultResponse parameterSc(Integer parameterSc) {
    this.parameterSc = parameterSc;
    return this;
  }

  /**
   * Parameter stancode
   *
   * @return parameterSc
   **/
  public Integer getParameterSc() {
    return parameterSc;
  }

  public void setParameterSc(Integer parameterSc) {
    this.parameterSc = parameterSc;
  }

  public DmpHydroApiResponsesResultResponse parameter(String parameter) {
    this.parameter = parameter;
    return this;
  }

  /**
   * Parameter name
   *
   * @return parameter
   **/
  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public DmpHydroApiResponsesResultResponse examinationTypeSc(Integer examinationTypeSc) {
    this.examinationTypeSc = examinationTypeSc;
    return this;
  }

  /**
   * Examination type stancode
   *
   * @return examinationTypeSc
   **/
  public Integer getExaminationTypeSc() {
    return examinationTypeSc;
  }

  public void setExaminationTypeSc(Integer examinationTypeSc) {
    this.examinationTypeSc = examinationTypeSc;
  }

  public DmpHydroApiResponsesResultResponse examinationType(String examinationType) {
    this.examinationType = examinationType;
    return this;
  }

  /**
   * Examination type name
   *
   * @return examinationType
   **/
  public String getExaminationType() {
    return examinationType;
  }

  public void setExaminationType(String examinationType) {
    this.examinationType = examinationType;
  }

  public DmpHydroApiResponsesResultResponse measurementDateTime(
      OffsetDateTime measurementDateTime) {
    this.measurementDateTime = measurementDateTime;
    return this;
  }

  /**
   * Measurement date time. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;.
   *
   * @return measurementDateTime
   **/
  public OffsetDateTime getMeasurementDateTime() {
    return measurementDateTime;
  }

  public void setMeasurementDateTime(OffsetDateTime measurementDateTime) {
    this.measurementDateTime = measurementDateTime;
  }

  public DmpHydroApiResponsesResultResponse result(Double result) {
    this.result = result;
    return this;
  }

  /**
   * Measurement result
   *
   * @return result
   **/
  public Double getResult() {
    return result;
  }

  public void setResult(Double result) {
    this.result = result;
  }

  public DmpHydroApiResponsesResultResponse resultElevationCorrected(
      Double resultElevationCorrected) {
    this.resultElevationCorrected = resultElevationCorrected;
    return this;
  }

  /**
   * Elevation corrected result. Only available for Water Level examination
   *
   * @return resultElevationCorrected
   **/
  public Double getResultElevationCorrected() {
    return resultElevationCorrected;
  }

  public void setResultElevationCorrected(Double resultElevationCorrected) {
    this.resultElevationCorrected = resultElevationCorrected;
  }

  public DmpHydroApiResponsesResultResponse unitSc(Integer unitSc) {
    this.unitSc = unitSc;
    return this;
  }

  /**
   * Unit stancode
   *
   * @return unitSc
   **/
  public Integer getUnitSc() {
    return unitSc;
  }

  public void setUnitSc(Integer unitSc) {
    this.unitSc = unitSc;
  }

  public DmpHydroApiResponsesResultResponse unit(String unit) {
    this.unit = unit;
    return this;
  }

  /**
   * Unit name
   *
   * @return unit
   **/
  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public DmpHydroApiResponsesResultResponse loggerId(String loggerId) {
    this.loggerId = loggerId;
    return this;
  }

  /**
   * Id of the logger that provided the measurement result
   *
   * @return loggerId
   **/
  public String getLoggerId() {
    return loggerId;
  }

  public void setLoggerId(String loggerId) {
    this.loggerId = loggerId;
  }

  public DmpHydroApiResponsesResultResponse formulaId(String formulaId) {
    this.formulaId = formulaId;
    return this;
  }

  /**
   * Formula id
   *
   * @return formulaId
   **/
  public String getFormulaId() {
    return formulaId;
  }

  public void setFormulaId(String formulaId) {
    this.formulaId = formulaId;
  }

  public DmpHydroApiResponsesResultResponse createdTimestamp(OffsetDateTime createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
    return this;
  }

  /**
   * The timestamp when the result was delivered/created. Must be defined without second component as an UTC timestamp in the RFC 3339 date+time format. For example &#x27;2023-09-21T14:34Z&#x27;.
   *
   * @return createdTimestamp
   **/
  public OffsetDateTime getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(OffsetDateTime createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public DmpHydroApiResponsesResultResponse reasonSc(Integer reasonSc) {
    this.reasonSc = reasonSc;
    return this;
  }

  /**
   * Reason stancode
   *
   * @return reasonSc
   **/
  public Integer getReasonSc() {
    return reasonSc;
  }

  public void setReasonSc(Integer reasonSc) {
    this.reasonSc = reasonSc;
  }

  public DmpHydroApiResponsesResultResponse reason(String reason) {
    this.reason = reason;
    return this;
  }

  /**
   * Reason name
   *
   * @return reason
   **/
  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public DmpHydroApiResponsesResultResponse vegetationIndex(Integer vegetationIndex) {
    this.vegetationIndex = vegetationIndex;
    return this;
  }

  /**
   * Vegetation index
   *
   * @return vegetationIndex
   **/
  public Integer getVegetationIndex() {
    return vegetationIndex;
  }

  public void setVegetationIndex(Integer vegetationIndex) {
    this.vegetationIndex = vegetationIndex;
  }

  public DmpHydroApiResponsesResultResponse refPointText(String refPointText) {
    this.refPointText = refPointText;
    return this;
  }

  /**
   * Reference point text
   *
   * @return refPointText
   **/
  public String getRefPointText() {
    return refPointText;
  }

  public void setRefPointText(String refPointText) {
    this.refPointText = refPointText;
  }

  public DmpHydroApiResponsesResultResponse distToFixPointM(Double distToFixPointM) {
    this.distToFixPointM = distToFixPointM;
    return this;
  }

  /**
   * Distance to fix point in meter (m)
   *
   * @return distToFixPointM
   **/
  public Double getDistToFixPointM() {
    return distToFixPointM;
  }

  public void setDistToFixPointM(Double distToFixPointM) {
    this.distToFixPointM = distToFixPointM;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiResponsesResultResponse dmpHydroApiResponsesResultResponse =
        (DmpHydroApiResponsesResultResponse) o;
    return Objects.equals(this.measurementPointNumber,
        dmpHydroApiResponsesResultResponse.measurementPointNumber) &&
        Objects.equals(this.measurementPointTypeSc,
            dmpHydroApiResponsesResultResponse.measurementPointTypeSc) &&
        Objects.equals(this.measurementPointType,
            dmpHydroApiResponsesResultResponse.measurementPointType) &&
        Objects.equals(this.parameterSc, dmpHydroApiResponsesResultResponse.parameterSc) &&
        Objects.equals(this.parameter, dmpHydroApiResponsesResultResponse.parameter) &&
        Objects.equals(this.examinationTypeSc,
            dmpHydroApiResponsesResultResponse.examinationTypeSc) &&
        Objects.equals(this.examinationType, dmpHydroApiResponsesResultResponse.examinationType) &&
        Objects.equals(this.measurementDateTime,
            dmpHydroApiResponsesResultResponse.measurementDateTime) &&
        Objects.equals(this.result, dmpHydroApiResponsesResultResponse.result) &&
        Objects.equals(this.resultElevationCorrected,
            dmpHydroApiResponsesResultResponse.resultElevationCorrected) &&
        Objects.equals(this.unitSc, dmpHydroApiResponsesResultResponse.unitSc) &&
        Objects.equals(this.unit, dmpHydroApiResponsesResultResponse.unit) &&
        Objects.equals(this.loggerId, dmpHydroApiResponsesResultResponse.loggerId) &&
        Objects.equals(this.formulaId, dmpHydroApiResponsesResultResponse.formulaId) &&
        Objects.equals(this.createdTimestamp,
            dmpHydroApiResponsesResultResponse.createdTimestamp) &&
        Objects.equals(this.reasonSc, dmpHydroApiResponsesResultResponse.reasonSc) &&
        Objects.equals(this.reason, dmpHydroApiResponsesResultResponse.reason) &&
        Objects.equals(this.vegetationIndex, dmpHydroApiResponsesResultResponse.vegetationIndex) &&
        Objects.equals(this.refPointText, dmpHydroApiResponsesResultResponse.refPointText) &&
        Objects.equals(this.distToFixPointM, dmpHydroApiResponsesResultResponse.distToFixPointM);
  }

  @Override
  public int hashCode() {
    return Objects.hash(measurementPointNumber, measurementPointTypeSc, measurementPointType,
        parameterSc, parameter, examinationTypeSc, examinationType, measurementDateTime, result,
        resultElevationCorrected, unitSc, unit, loggerId, formulaId, createdTimestamp, reasonSc,
        reason, vegetationIndex, refPointText, distToFixPointM);
  }


  @Override
  public String toString() {

    String sb = "class DmpHydroApiResponsesResultResponse {\n" +
        "    measurementPointNumber: " + toIndentedString(measurementPointNumber) + "\n" +
        "    measurementPointTypeSc: " + toIndentedString(measurementPointTypeSc) + "\n" +
        "    measurementPointType: " + toIndentedString(measurementPointType) + "\n" +
        "    parameterSc: " + toIndentedString(parameterSc) + "\n" +
        "    parameter: " + toIndentedString(parameter) + "\n" +
        "    examinationTypeSc: " + toIndentedString(examinationTypeSc) + "\n" +
        "    examinationType: " + toIndentedString(examinationType) + "\n" +
        "    measurementDateTime: " + toIndentedString(measurementDateTime) + "\n" +
        "    result: " + toIndentedString(result) + "\n" +
        "    resultElevationCorrected: " + toIndentedString(resultElevationCorrected) + "\n" +
        "    unitSc: " + toIndentedString(unitSc) + "\n" +
        "    unit: " + toIndentedString(unit) + "\n" +
        "    loggerId: " + toIndentedString(loggerId) + "\n" +
        "    formulaId: " + toIndentedString(formulaId) + "\n" +
        "    createdTimestamp: " + toIndentedString(createdTimestamp) + "\n" +
        "    reasonSc: " + toIndentedString(reasonSc) + "\n" +
        "    reason: " + toIndentedString(reason) + "\n" +
        "    vegetationIndex: " + toIndentedString(vegetationIndex) + "\n" +
        "    refPointText: " + toIndentedString(refPointText) + "\n" +
        "    distToFixPointM: " + toIndentedString(distToFixPointM) + "\n" +
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
