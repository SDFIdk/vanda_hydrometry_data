package dk.dataforsyningen.vanda_hydrometry_data.components;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public class VandaHUtility {
	
	public static String BOLD_ON = "\033[1m";
	public static String ITALIC_ON = "\033[3m";
	public static String FORMAT_OFF = "\033[0m";
	
	public static void logAndPrint(Logger log, Level level, boolean consolePrint, String message) {
		VandaHUtility.logAndPrint(log, level, consolePrint, message, null);
	}
	
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
