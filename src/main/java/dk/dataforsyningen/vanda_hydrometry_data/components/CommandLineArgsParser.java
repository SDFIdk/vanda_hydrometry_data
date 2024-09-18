package dk.dataforsyningen.vanda_hydrometry_data.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataApplication;

/**
 * Parse the command line parameters and extract a list of commands.
 * Commands are simple strings.
 * Options starts with "--" and are disconsidered - they are parsed by the @Configuration component.
 * 
 * @author Radu Dudici
 */
@Component
public class CommandLineArgsParser {
	
	private static final Logger log = LoggerFactory.getLogger(CommandLineArgsParser.class);
	
	private ArrayList<String> commands;
	
	public CommandLineArgsParser() {
		this.clear();
	}
	
	public void clear() {
		commands = new ArrayList<>();
	}
	
	public void parse(String... args) {
		for(String arg : args) {
			if (arg != null && arg.length() > 0 && !arg.startsWith("--")) {
				//do not consider application's startup class as a command.
				if (!arg.toLowerCase().equals(VandaHydrometryDataApplication.class.getCanonicalName().toLowerCase())) {
					commands.add(arg.toLowerCase());
					log.debug("Added command '" + arg.toLowerCase() + "'");
				}
			}
		}
	}
		
	public ArrayList<String> getCommands() {
		return commands;
	}
	
	/**
	 * Only checks that a command with the given name exists
	 * @param name
	 * @return
	 */
	public boolean hasCommand(String name) {
		return commands.contains(name.toLowerCase());
	}
	
/*
	private Object parseValues(String value) {
		if (value == null) return null;
		
		if (value.contains(",")) {
			return Arrays.stream(value.split(",")).filter(v -> (v != null && v.length() > 0)).toArray();
		} 
		return value;
	}
		
	public String getStringOption(String name) {
		name = name.toLowerCase();
		return options.containsKey(name) && options.get(name) != null ? 
				(options.get(name).getClass().isArray() ?
						String.join(",", Arrays.stream((Object[])options.get(name)).toArray(String[]::new)) :
						options.get(name).toString()) 
				: null;
	}
	
	public Integer getIntegerOption(String name) {
		name = name.toLowerCase();
		if (options.containsKey(name) && options.get(name) != null && !options.get(name).getClass().isArray()) {
			try {
				return Integer.parseInt(options.get(name).toString());
			} catch (NumberFormatException e) {
				// do nothing
			}
		}
		return null;
	}
	
	public Double getDoubleOption(String name) {
		name = name.toLowerCase();
		if (options.containsKey(name) && options.get(name) != null && !options.get(name).getClass().isArray()) {
			try {
				return Double.parseDouble(options.get(name).toString());
			} catch (NumberFormatException e) {
				// do nothing
			}
		}
		return null;
	}
	
	public String[] getOptionAsStringArray(String name) {
		name = name.toLowerCase();
		if (options.containsKey(name) && options.get(name) != null) {
			return options.get(name).getClass().isArray() ?
					Arrays.stream((Object[])options.get(name)).toArray(String[]::new) :
 					new String[] { options.get(name).toString() };
		}
		return null;
	}
	
	public Integer[] getOptionAsIntArray(String name) {
		name = name.toLowerCase();
		if (options.containsKey(name) && options.get(name) != null) {
			return Arrays.stream(getOptionAsStringArray(name)).filter(value -> isInteger(value)).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
		}
		return null;
	}
	
	public Double[] getOptionAsDoubleArray(String name) {
		name = name.toLowerCase();
		if (options.containsKey(name) && options.get(name) != null) {
			return Arrays.stream(getOptionAsStringArray(name)).filter(value -> isDouble(value) || isInteger(value)).mapToDouble(Double::parseDouble).boxed().toArray(Double[]::new);
		}
		return null;
	}
	
	private boolean isDouble(String val) {
		try {
			Double.parseDouble(val);
			return val.contains(".");
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private boolean isInteger(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
*/	
}
