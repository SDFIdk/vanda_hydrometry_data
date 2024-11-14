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
 * DmpHydroApiResponsesExaminationTypeResponse
 */
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
     *
     * @return examinationTypeSc
     **/
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
     *
     * @return examinationTypeName
     **/
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
     *
     * @return measurementPointTypeSc
     **/
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
     *
     * @return parameterSc
     **/
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
     *
     * @return unitSc
     **/
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
     *
     * @return min
     **/
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
     *
     * @return max
     **/
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
     *
     * @return measurementIntervalMin
     **/
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
     *
     * @return decimals
     **/
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
     *
     * @return description
     **/
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
     *
     * @return reasonCodesSc
     **/
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

        String sb = "class DmpHydroApiResponsesExaminationTypeResponse {\n" +
                "    examinationTypeSc: " + toIndentedString(examinationTypeSc) + "\n" +
                "    examinationTypeName: " + toIndentedString(examinationTypeName) + "\n" +
                "    measurementPointTypeSc: " + toIndentedString(measurementPointTypeSc) + "\n" +
                "    parameterSc: " + toIndentedString(parameterSc) + "\n" +
                "    unitSc: " + toIndentedString(unitSc) + "\n" +
                "    min: " + toIndentedString(min) + "\n" +
                "    max: " + toIndentedString(max) + "\n" +
                "    measurementIntervalMin: " + toIndentedString(measurementIntervalMin) + "\n" +
                "    decimals: " + toIndentedString(decimals) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    reasonCodesSc: " + toIndentedString(reasonCodesSc) + "\n" +
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
