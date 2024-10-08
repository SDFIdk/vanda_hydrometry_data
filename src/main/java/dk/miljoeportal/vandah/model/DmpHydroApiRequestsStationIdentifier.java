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
/**
 * A station can be identified using either Id or OperatorStationId
 */
@Schema(description = "A station can be identified using either Id or OperatorStationId")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-27T10:11:34.799092956+02:00[Europe/Copenhagen]")

public class DmpHydroApiRequestsStationIdentifier {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("operatorStationId")
  private String operatorStationId = null;

  public DmpHydroApiRequestsStationIdentifier id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Vanda station id
   * @return id
  **/
  @Schema(description = "Vanda station id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DmpHydroApiRequestsStationIdentifier operatorStationId(String operatorStationId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiRequestsStationIdentifier dmpHydroApiRequestsStationIdentifier = (DmpHydroApiRequestsStationIdentifier) o;
    return Objects.equals(this.id, dmpHydroApiRequestsStationIdentifier.id) &&
        Objects.equals(this.operatorStationId, dmpHydroApiRequestsStationIdentifier.operatorStationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, operatorStationId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiRequestsStationIdentifier {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    operatorStationId: ").append(toIndentedString(operatorStationId)).append("\n");
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
