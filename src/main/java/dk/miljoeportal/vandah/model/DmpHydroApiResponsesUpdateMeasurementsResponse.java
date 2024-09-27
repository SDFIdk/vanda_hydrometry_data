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
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesFailedResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesSuccessfulResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Date;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
/**
 * DmpHydroApiResponsesUpdateMeasurementsResponse
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-27T10:11:34.799092956+02:00[Europe/Copenhagen]")

public class DmpHydroApiResponsesUpdateMeasurementsResponse {
  @JsonProperty("created")
  private OffsetDateTime created = null;

  @JsonProperty("transactionId")
  private UUID transactionId = null;

  @JsonProperty("successful")
  private List<DmpHydroApiResponsesSuccessfulResponse> successful = null;

  @JsonProperty("failed")
  private List<DmpHydroApiResponsesFailedResponse> failed = null;

  public DmpHydroApiResponsesUpdateMeasurementsResponse created(OffsetDateTime created) {
    this.created = created;
    return this;
  }

   /**
   * The time data is stored in the database for the batch
   * @return created
  **/
  @Schema(description = "The time data is stored in the database for the batch")
  public OffsetDateTime getCreated() {
    return created;
  }

  public void setCreated(OffsetDateTime created) {
    this.created = created;
  }

  public DmpHydroApiResponsesUpdateMeasurementsResponse transactionId(UUID transactionId) {
    this.transactionId = transactionId;
    return this;
  }

   /**
   * Batch transaction id
   * @return transactionId
  **/
  @Schema(description = "Batch transaction id")
  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }

  public DmpHydroApiResponsesUpdateMeasurementsResponse successful(List<DmpHydroApiResponsesSuccessfulResponse> successful) {
    this.successful = successful;
    return this;
  }

  public DmpHydroApiResponsesUpdateMeasurementsResponse addSuccessfulItem(DmpHydroApiResponsesSuccessfulResponse successfulItem) {
    if (this.successful == null) {
      this.successful = new ArrayList<DmpHydroApiResponsesSuccessfulResponse>();
    }
    this.successful.add(successfulItem);
    return this;
  }

   /**
   * A list of measurements that were stored
   * @return successful
  **/
  @Schema(description = "A list of measurements that were stored")
  public List<DmpHydroApiResponsesSuccessfulResponse> getSuccessful() {
    return successful;
  }

  public void setSuccessful(List<DmpHydroApiResponsesSuccessfulResponse> successful) {
    this.successful = successful;
  }

  public DmpHydroApiResponsesUpdateMeasurementsResponse failed(List<DmpHydroApiResponsesFailedResponse> failed) {
    this.failed = failed;
    return this;
  }

  public DmpHydroApiResponsesUpdateMeasurementsResponse addFailedItem(DmpHydroApiResponsesFailedResponse failedItem) {
    if (this.failed == null) {
      this.failed = new ArrayList<DmpHydroApiResponsesFailedResponse>();
    }
    this.failed.add(failedItem);
    return this;
  }

   /**
   * A list of measurements that were failed to store
   * @return failed
  **/
  @Schema(description = "A list of measurements that were failed to store")
  public List<DmpHydroApiResponsesFailedResponse> getFailed() {
    return failed;
  }

  public void setFailed(List<DmpHydroApiResponsesFailedResponse> failed) {
    this.failed = failed;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiResponsesUpdateMeasurementsResponse dmpHydroApiResponsesUpdateMeasurementsResponse = (DmpHydroApiResponsesUpdateMeasurementsResponse) o;
    return Objects.equals(this.created, dmpHydroApiResponsesUpdateMeasurementsResponse.created) &&
        Objects.equals(this.transactionId, dmpHydroApiResponsesUpdateMeasurementsResponse.transactionId) &&
        Objects.equals(this.successful, dmpHydroApiResponsesUpdateMeasurementsResponse.successful) &&
        Objects.equals(this.failed, dmpHydroApiResponsesUpdateMeasurementsResponse.failed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(created, transactionId, successful, failed);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiResponsesUpdateMeasurementsResponse {\n");
    
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    successful: ").append(toIndentedString(successful)).append("\n");
    sb.append("    failed: ").append(toIndentedString(failed)).append("\n");
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
