package dk.dataforsyningen.vanda_hydrometry_data.components;

import org.slf4j.Logger;
import org.slf4j.event.Level;

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
}
