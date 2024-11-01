package dk.dataforsyningen.vanda_hydrometry_data.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperHelper {

    private static final Logger log = LoggerFactory.getLogger(MapperHelper.class);

    /**
     * Checks for null, is empty "" or is blank " "
     * @param s input string
     * @return validated string
     */
    public static String validateString(String s) {
        if (s == null || s.isEmpty() || s.isBlank()) {
            return null;
        }
        else {
            return s;
        }
    }

    /**
     * Convert Srid to an Integer
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
