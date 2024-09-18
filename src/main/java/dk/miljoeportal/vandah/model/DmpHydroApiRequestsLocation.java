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
 * A SRID:25832 value
 */
@Schema(description = "A SRID:25832 value")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-09-18T14:15:50.310248991+02:00[Europe/Copenhagen]")

public class DmpHydroApiRequestsLocation {
  @JsonProperty("x")
  private Double x = null;

  @JsonProperty("y")
  private Double y = null;

  @JsonProperty("srid")
  private String srid = null;

  public DmpHydroApiRequestsLocation x(Double x) {
    this.x = x;
    return this;
  }

   /**
   * The X coordinate in SRID:25832
   * @return x
  **/
  @Schema(required = true, description = "The X coordinate in SRID:25832")
  public Double getX() {
    return x;
  }

  public void setX(Double x) {
    this.x = x;
  }

  public DmpHydroApiRequestsLocation y(Double y) {
    this.y = y;
    return this;
  }

   /**
   * The Y coordinate in SRID:25832
   * @return y
  **/
  @Schema(required = true, description = "The Y coordinate in SRID:25832")
  public Double getY() {
    return y;
  }

  public void setY(Double y) {
    this.y = y;
  }

  public DmpHydroApiRequestsLocation srid(String srid) {
    this.srid = srid;
    return this;
  }

   /**
   * Must be 25832
   * @return srid
  **/
  @Schema(required = true, description = "Must be 25832")
  public String getSrid() {
    return srid;
  }

  public void setSrid(String srid) {
    this.srid = srid;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DmpHydroApiRequestsLocation dmpHydroApiRequestsLocation = (DmpHydroApiRequestsLocation) o;
    return Objects.equals(this.x, dmpHydroApiRequestsLocation.x) &&
        Objects.equals(this.y, dmpHydroApiRequestsLocation.y) &&
        Objects.equals(this.srid, dmpHydroApiRequestsLocation.srid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, srid);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DmpHydroApiRequestsLocation {\n");
    
    sb.append("    x: ").append(toIndentedString(x)).append("\n");
    sb.append("    y: ").append(toIndentedString(y)).append("\n");
    sb.append("    srid: ").append(toIndentedString(srid)).append("\n");
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