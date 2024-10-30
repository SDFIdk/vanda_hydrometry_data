package dk.dataforsyningen.vanda_hydrometry_data.mapper;

public class ValidateHelper {
    /**
     * Checks for null, is empty "" or is blank " "
     * @param s input string
     * @return validated string
     */
    public String validate(String s) {
        if (s == null || s.isEmpty() || s.isBlank()) {
            return null;
        }
        else {
            return s;
        }
    }
}
