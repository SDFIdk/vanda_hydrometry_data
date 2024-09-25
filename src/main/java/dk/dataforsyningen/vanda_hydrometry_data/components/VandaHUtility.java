package dk.dataforsyningen.vanda_hydrometry_data.components;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
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
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	
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
	 * Takes a string representing a data in deviations from the following form and converts into the form "YYYY-MM-DD HH:MM:SS"
	 * 
	 * Input format: yyyy-mm-ddThh:mm:ssZ
	 * where there may be deviations like:
	 *   missing T or Z
	 *   month, day ,hours, minutes and seconds may have 1 digit 
	 *   
	 * @param date
	 * @return date string YYY-MM-DD HH:MM:SS
	 */
	public static String normalizeDate(String dateStr) {
        String regex = "(\\d{4})-(\\d{1,2})-(\\d{1,2})(?:[T\\s](\\d{1,2}):(\\d{1,2})(?::(\\d{1,2}))?)?Z?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateStr);

        if (matcher.matches()) {
        	String year = matcher.group(1);
            String month = String.format("%02d", Integer.parseInt(matcher.group(2)));
            String day = String.format("%02d", Integer.parseInt(matcher.group(3)));
            String hour = String.format("%02d", matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : 0);
            String minute = String.format("%02d", matcher.group(5) != null ? Integer.parseInt(matcher.group(5)) : 0);

            return String.format("%s-%s-%s %s:%s", year, month, day, hour, minute);
        } else {
            return null;
        }
	}
	
	
	/**
	 * Converts a date in UTC format yyyy-mm-ddThh:mm:ssZ into a Date object
	 * @param dateStr
	 * @return Date object
	 */
	public static Date parseUtcDate(String dateStr) {
		if (dateStr == null) return null;
		try {
			return formatter.parse(VandaHUtility.normalizeDate(dateStr));
		} catch (Exception ex) {
			return null;
		}
	}
}
