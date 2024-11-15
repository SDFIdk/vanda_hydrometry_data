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
import java.util.Objects;

/**
 * DmpHydroApiResponsesLocationResponse
 */
public class DmpHydroApiResponsesLocationResponse {
  @JsonProperty("x")
  private Double x = null;

  @JsonProperty("y")
  private Double y = null;

  @JsonProperty("srid")
  private String srid = null;

  public DmpHydroApiResponsesLocationResponse x(Double x) {
    this.x = x;
    return this;
  }

  /**
   * X coordinate in UTM32N
   *
   * @return x
   **/
  public Double getX() {
    return x;
  }

  public void setX(Double x) {
    this.x = x;
  }

  public DmpHydroApiResponsesLocationResponse y(Double y) {
    this.y = y;
    return this;
  }

  /**
   * Y coordinate in UTM32N
   *
   * @return y
   **/
  public Double getY() {
    return y;
  }

  public void setY(Double y) {
    this.y = y;
  }

  public DmpHydroApiResponsesLocationResponse srid(String srid) {
    this.srid = srid;
    return this;
  }

  /**
   * Location SRID
   *
   * @return srid
   **/
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
    DmpHydroApiResponsesLocationResponse dmpHydroApiResponsesLocationResponse =
        (DmpHydroApiResponsesLocationResponse) o;
    return Objects.equals(this.x, dmpHydroApiResponsesLocationResponse.x) &&
        Objects.equals(this.y, dmpHydroApiResponsesLocationResponse.y) &&
        Objects.equals(this.srid, dmpHydroApiResponsesLocationResponse.srid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, srid);
  }


  @Override
  public String toString() {

    String sb = "class DmpHydroApiResponsesLocationResponse {\n" +
        "    x: " + toIndentedString(x) + "\n" +
        "    y: " + toIndentedString(y) + "\n" +
        "    srid: " + toIndentedString(srid) + "\n" +
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
