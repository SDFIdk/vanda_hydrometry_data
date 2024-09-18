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
import java.util.Date;
/**
 * DmpHydroApiResponsesFailedResponse
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-18T14:15:50.310248991+02:00[Europe/Copenhagen]")

public class DmpHydroApiResponsesFailedResponse {
  @JsonProperty("timestamp")
  private Date timestamp = null;

  @JsonProperty("isSuccess")
  private Boolean isSuccess = null;

  @JsonProperty("errorCode")
  private Integer errorCode = null;

  @JsonProperty("errorMessage")
  private String errorMessage = null;

  public DmpHydroApiResponsesFailedResponse timestamp(Date timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Measurement datetime
   * @return timestamp
  **/
  @Schema(description = "Measurement datetime")
  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public DmpHydroApiResponsesFailedResponse isSuccess(Boolean isSuccess) {
    this.isSuccess = isSuccess;
    return this;
  }

   /**
   * If transaction-based, this will be &#x27;true&#x27; for the measurement which succeeds and &#x27;false&#x27; for the ones which fails
   * @return isSuccess
  **/
  @Schema(description = "If transaction-based, this will be 'true' for the measurement which succeeds and 'false' for the ones which fails")
  public Boolean isIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(Boolean isSuccess) {
    this.isSuccess = isSuccess;
  }

  public DmpHydroApiResponsesFailedResponse errorCode(Integer errorCode) {
    this.errorCode = errorCode;
    return this;
  }

   /**
   * Error code
   * @return errorCode
  **/
  @Schema(description = "Error code")
  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public DmpHydroApiResponsesFailedResponse errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

   /**
   * Error message
   * @return errorMessage
  **/
  @Schema(description = "Error message")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiResponsesFailedResponse dmpHydroApiResponsesFailedResponse = (DmpHydroApiResponsesFailedResponse) o;
    return Objects.equals(this.timestamp, dmpHydroApiResponsesFailedResponse.timestamp) &&
        Objects.equals(this.isSuccess, dmpHydroApiResponsesFailedResponse.isSuccess) &&
        Objects.equals(this.errorCode, dmpHydroApiResponsesFailedResponse.errorCode) &&
        Objects.equals(this.errorMessage, dmpHydroApiResponsesFailedResponse.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, isSuccess, errorCode, errorMessage);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiResponsesFailedResponse {\n");
    
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    isSuccess: ").append(toIndentedString(isSuccess)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
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
