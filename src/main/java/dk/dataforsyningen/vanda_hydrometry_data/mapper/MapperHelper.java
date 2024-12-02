package dk.dataforsyningen.vanda_hydrometry_data.mapper;

public class MapperHelper {

  /**
   * Checks for null, is empty "" or is blank " "
   *
   * @param s input string
   * @return validated string
   */
  public static String validateString(String s) {
    if (s == null || s.isBlank()) {
      return null;
    } else {
      return s;
    }
  }

  /**
   * Convert Srid to an Integer
   *
   * @param srid String
   * @return an Integer or null
   */
  public static Integer convertSrid(String srid) {
    try {
      return Integer.parseInt(srid);
    } catch (NumberFormatException exception) {
      throw new NumberFormatException("Could not parse SRID. " + exception.getMessage());
    }
  }
}
