package dk.dataforsyningen.vanda_hydrometry_data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CommandLineArgsParser {

	private HashMap<String,Object> options;
	private ArrayList<String> commands;
	
	public CommandLineArgsParser() {
		this.clear();
	}
	
	public void clear() {
		options = new HashMap<>();
		commands = new ArrayList<>();
	}
	
	public void parse(String... args) {
		for(String arg : args) {
			if (arg.startsWith("--") && arg.contains("=")) {
				int pos = arg.indexOf("=");
				String key = arg.substring(2, pos);
				Object val = parseValues(arg.substring(pos+1));
				if (key != null && key.length() > 0) options.put(key.toLowerCase(), val);
			} else if (arg.startsWith("--")) {
				String key = arg.substring(2);
				String val = null;
				if (key != null && key.length() > 0) options.put(key.toLowerCase(), val);
			} else if (arg != null && arg.length() > 0){
				commands.add(arg.toLowerCase());
			}
		}
	}
	
	private Object parseValues(String value) {
		if (value == null) return null;
		
		if (value.contains(",")) {
			return Arrays.stream(value.split(",")).filter(v -> (v != null && v.length() > 0)).toArray();
		} 
		return value;
	}
	
	public HashMap<String,Object> getOptions() {
		return options;
	}
	
	public ArrayList<String> getCommands() {
		return commands;
	}
	
	public boolean hasParameter(String name) {
		return options.containsKey(name.toLowerCase()) || commands.contains(name.toLowerCase());
	}
	
	public Object getParameter(String name) {
		return options.containsKey(name.toLowerCase()) ? options.get(name.toLowerCase()) : null;
	}
	
	public boolean containsParam(String name) {
		return options.containsKey(name.toLowerCase()) || commands.contains(name.toLowerCase());
	}
		
	public String getStringParameter(String name) {
		name = name.toLowerCase();
		return options.containsKey(name) && options.get(name) != null ? 
				(options.get(name).getClass().isArray() ?
						String.join(",", Arrays.stream((Object[])options.get(name)).toArray(String[]::new)) :
						options.get(name).toString()) 
				: null;
	}
	
	public Integer getIntegerParameter(String name) {
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
	
	public Double getDoubleParameter(String name) {
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
	
	public String[] getParameterAsStringArray(String name) {
		name = name.toLowerCase();
		if (options.containsKey(name) && options.get(name) != null) {
			return options.get(name).getClass().isArray() ?
					Arrays.stream((Object[])options.get(name)).toArray(String[]::new) :
 					new String[] { options.get(name).toString() };
		}
		return null;
	}
	
	public Integer[] getParameterAsIntArray(String name) {
		name = name.toLowerCase();
		if (options.containsKey(name) && options.get(name) != null) {
			return Arrays.stream(getParameterAsStringArray(name)).filter(value -> isInteger(value)).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
		}
		return null;
	}
	
	public Double[] getParameterAsDoubleArray(String name) {
		name = name.toLowerCase();
		if (options.containsKey(name) && options.get(name) != null) {
			return Arrays.stream(getParameterAsStringArray(name)).filter(value -> isDouble(value) || isInteger(value)).mapToDouble(Double::parseDouble).boxed().toArray(Double[]::new);
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
}
