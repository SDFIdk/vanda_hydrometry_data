package dk.dataforsyningen.vanda_hydrometry_data.components;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.springframework.http.client.ClientHttpResponse;

public class VandaHUtility {
	
	public static String BOLD_ON = "\033[1m";
	public static String ITALIC_ON = "\033[3m";
	public static String FORMAT_OFF = "\033[0m";
	
	/**
	 *  See {@link #logAndPrint(Logger log, Level level, boolean consolePrint, String message, Throwable exception)}
	 *  
	 * @param log
	 * @param level
	 * @param consolePrint
	 * @param message
	 */
	public static void logAndPrint(Logger log, Level level, boolean consolePrint, String message) {
		VandaHUtility.logAndPrint(log, level, consolePrint, message, null);
	}
	
	/**
	 * Function to add a message to the log file as well as print it to the console.
	 * 
	 * For logging to file use the log and level parameters.
	 * For printing to the console use consolePrint = true (unless level = ERROR) and level (optional). 
	 * 
	 * @param log the Logger object if the file logging is desired.
	 * @param level the log level used both by the file logger and as prefix for the printed message.
	 * @param consolePrint enables or disables the printing on the console unless it is an error (which is always printed).
	 * @param message (required) the message to be logged or printed.
	 * @param exception add an exception besides the message (both for logging and printing)
	 */
	public static void logAndPrint(Logger log, Level level, boolean consolePrint, String message, Throwable exception) {
		if (log != null) {
			if (Level.DEBUG.equals(level)) {
				log.debug(message);
			} else if (Level.INFO.equals(level)) {
				log.info(message);
			} else if (Level.WARN.equals(level)) {
				if (exception == null) {
					log.warn(message);
				} else {
					log.warn(message, exception);
				}
			} else if (Level.ERROR.equals(level)) {
				if (exception == null) {
					log.error(message);
				} else {
					log.error(message, exception);
				}
			} else if (Level.TRACE.equals(level)) {
				log.trace(message);
			}
		}
		message = (level == null || Level.INFO.equals(level) ? "" : level.name() + ": ") + message + (exception != null ? " [" + exception.getMessage() + "]" : "");
		if (Level.ERROR.equals(level)) {
			System.err.println(message);
		} else if (consolePrint) {
			System.out.println(message);
		}
	}
	
	/**
	 * Converts Date object representing a date like "2024-09-25T23:00:00.00Z" to an OffsetDateTime in UTC timezone 
	 * @return
	 */
	public static OffsetDateTime dateToOfssetDateTime(Date date) {
		return date != null ? date.toInstant().atOffset(ZoneOffset.UTC) : null;
	}
	
	/**
	 * Returns the value for the given key from a json given as string
	 * @param key
	 * @return value as string
	 */
	public static String valueFromJson(String json, String key) {
		String message = null;
		if (json == null || key == null) return null;
		try { 
			JSONObject bodyObj = new JSONObject(json);
			message = bodyObj.has("message") ? "" + bodyObj.get("message") : null;
		} catch (Exception ex) {}
		return message;
	}
	
	/**
	 * Returns the value for the given key from a json given as string in a Http Response
	 * @param key
	 * @return value as string
	 */
	public static String valueFromJson(ClientHttpResponse response, String key) {
		if (response == null) return null;
		
		String message = null;
		try {
			if (response.getBody() != null) {
				message = valueFromJson(new String(response.getBody().readAllBytes()), key);
			}
		} catch(IOException ex) {
			//Do nothing
		}
		return message;
	}
	
	
	/**
	 * Takes a string representing a data with possible deviations from the following form and converts into the form "yyyy-mm-ddThh:mm:ss.SSS[Z]"
	 * 
	 * Input format: yyyy-mm-ddThh:mm:ss.sssZ
	 * where there may be deviations like:
	 *   missing T or Z
	 *   month, day ,hours, minutes and seconds or milliseconds may have 1 digit or missing from the end 
	 *   
	 * @param date
	 * @param withoutSeconds if true the output will not contain the seconds
	 * @return date string yyyy-mm-ddThh:mm:ss.SSS[Z]
	 */
	private static String normalizeDate(String dateStr, boolean withoutSeconds) {
        String regex = "(\\d{4})-(\\d{1,2})-(\\d{1,2})(?:[T\\s](\\d{1,2}):(\\d{1,2})(?::(\\d{1,2}))?(?:\\.(\\d{1,3}))?)?(Z)?";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(dateStr);

        if (matcher.matches()) {
        	String year = matcher.group(1);
            String month = String.format("%02d", Integer.parseInt(matcher.group(2)));
            String day = String.format("%02d", Integer.parseInt(matcher.group(3)));
            String hour = String.format("%02d", matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : 0);
            String minute = String.format("%02d", matcher.group(5) != null ? Integer.parseInt(matcher.group(5)) : 0);
            String seconds = String.format("%02d", matcher.group(6) != null ? Integer.parseInt(matcher.group(6)) : 0);
            String millis = String.format("%03d", matcher.group(7) != null ? Integer.parseInt(matcher.group(7)) : 0);
            boolean utc = matcher.group(8) != null;

            return withoutSeconds ? String.format("%s-%s-%sT%s:%s%s", year, month, day, hour, minute, utc ? "Z" : "")
            		: String.format("%s-%s-%sT%s:%s:%s.%s%s", year, month, day, hour, minute, seconds, millis, utc ? "Z" : "");
        } else {
            return null;
        }
	}
	
	
	/**
	 * This will not return seconds and milliseconds and will be in UTC time zone
	 * @return OffsetDateTime in UTC
	 */
	public static OffsetDateTime parseForAPI(String dateStr) throws DateTimeParseException {
		if (dateStr == null) return null;
		OffsetDateTime odt = null;
		String normalized = normalizeDate(dateStr, true);
		if (normalized.endsWith("Z")) {
			odt = OffsetDateTime.parse(normalized);
		} else {
			LocalDateTime ldt = LocalDateTime.parse(normalized, DateTimeFormatter.ISO_DATE_TIME);
			odt = ldt.atOffset(ZoneId.systemDefault().getRules().getOffset(ldt)) //convert LDT to ODT using the system's default TZ rules
					.withOffsetSameInstant(ZoneOffset.UTC); //convert ODT to UTC timezone
		}
		return odt;
	}
	
	/**
	 * Converts a date in UTC format yyyy-mm-ddThh:mm:ss.nnZ into a OffsetDateTime object
	 * @param dateStr
	 * @return OffsetDateTime object
	 */
	public static OffsetDateTime parseUtcOffsetDateTime(String dateStr) {
		if (dateStr == null) return null;
		OffsetDateTime odt = null;
		try {
			String normalized = normalizeDate(dateStr, false);
			if (normalized.endsWith("Z")) {
				odt = OffsetDateTime.parse(normalized);
			} else {
				LocalDateTime ldt = LocalDateTime.parse(normalized, DateTimeFormatter.ISO_DATE_TIME);
				odt = ldt.atOffset(ZoneId.systemDefault().getRules().getOffset(ldt)) //convert LDT to ODT using the system's default TZ rules
						.withOffsetSameInstant(ZoneOffset.UTC); //convert ODT to UTC time zone
			}
		} catch (Exception ex) {
			//Do nothing
		}
		return odt;
	}
	
	/**
	 * Convert a UTC date string into a Date.
	 * see {@link parseUtcOffsetDateTime(String dateStr)}
	 * 
	 * @param dateStr UTC date string
	 * @return Date
	 */
	public static Date parseUtcDate(String dateStr) {
		if (dateStr == null) return null;
		
		OffsetDateTime odt = parseUtcOffsetDateTime(dateStr);
		
		return odt != null ? Date.from(odt.toInstant()) : null;
	}
}
