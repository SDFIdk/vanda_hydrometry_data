package dk.dataforsyningen.vanda_hydrometry_data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * Configuration class.
 * Reads and provides the config from properties file.
 * 
 * @author Radu Dudici
 */
@Configuration
public class VandaHydrometryDataConfig {
	
	private DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/* Values from the properties file */
	
	@Value("${dmp.vandah.api.url:#{null}}")
	@Getter String vandahDmpApiUrl;
	
	/* Option values from the command line */
	
	/*
		help,
		savedb,
		verbose,
		stationid,
		examinationtypesc,
		parametersc,
		operatorstationid,
		withresultsafter,
		withresultscreatedafter,
		measurementpointnumber,
		from,
		to,
		createdafter
	*/

	@Value("${help:#{null}}")
	private String help; //boolean
	
	@Value("${verbose:#{null}}")
	private String verbose;  //boolean
	
	@Value("${savedb:#{null}}")
	private String saveDb;  //boolean
	
	@Value("${stationid:#{null}}")
	@Getter private String stationId;
	
	@Value("${examinationtypesc:#{null}}")
	private String examinationTypeSc;  //int[]
	
	@Value("${parametersc:#{null}}")
	private String parameterSc; //Integer
	
	@Value("${operatorstationid:#{null}}")
	@Getter private String operatorStationId;
	
	@Value("${measurementpointnumber:#{null}}")
	private String measurementPointNumber;  //Integer
	
	@Value("${withresultsafter:#{null}}")
	private String withResultsAfter; //date
	
	@Value("${withresultscreatedafter:#{null}}")
	private String withResultsCreatedAfter;  //date
	
	@Value("${from:#{null}}")
	private String from;  //date
	
	@Value("${to:#{null}}")
	private String to;  //date
	
	@Value("${createdafter:#{null}}")
	private String createdAfter;  //date
	
	
	/* Getters */
	
	public boolean isHelp() {
		return help != null;
	}
	
	public boolean isVerbose() {
		return verbose != null;
	}
	
	public boolean isSaveDb() {
		return saveDb != null;
	}
	
	public int[] getExaminationTypeSc() {
		if (examinationTypeSc == null || examinationTypeSc.length() == 0) {
			return null;
		} else if (examinationTypeSc.contains(",")) {
			return Arrays.stream(examinationTypeSc.split(",")).filter(v -> (v != null && v.length() > 0)).
					filter(value ->
							{
								try {
									Integer.parseInt(value);
									return true;
								} catch (NumberFormatException e) {
									return false;
								}
							}).mapToInt(Integer::parseInt).toArray();
		}
		try {
			return new int[] {Integer.parseInt(examinationTypeSc)};
		} catch (NumberFormatException e) {
			//do nothing
		}
		return null;
	}
	
	public Integer getParameterSc() {
		try {
			return Integer.parseInt(parameterSc);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public Integer getMeasurementPointNumber() {
		try {
			return Integer.parseInt(measurementPointNumber);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public LocalDateTime getWithResultsAfter() {
		
		//Date in = new Date();
		//LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
		
		if (withResultsAfter == null) return null;
		try {
			return LocalDateTime.parse(parseDate(withResultsAfter), datePattern);
		} catch (DateTimeParseException | NullPointerException ex) {
			return null;
		}
	}
	
	public LocalDateTime getWithResultsCreatedAfter() {
		if (withResultsCreatedAfter == null) return null;
		try {
			return LocalDateTime.parse(parseDate(withResultsCreatedAfter), datePattern);
		} catch (DateTimeParseException | NullPointerException ex) {
			return null;
		}
	}
	
	public LocalDateTime getFrom() {
		if (from == null) return null;
		try {
			return LocalDateTime.parse(parseDate(from), datePattern);
		} catch (DateTimeParseException | NullPointerException ex) {
			return null;
		}
	}
	
	public LocalDateTime getTo() {
		if (to == null) return null;
		try {
			return LocalDateTime.parse(parseDate(to), datePattern);
		} catch (DateTimeParseException | NullPointerException ex) {
			return null;
		}
	}
	
	public LocalDateTime getCreatedAfter() {
		if (createdAfter == null) return null;
		try {
			return LocalDateTime.parse(parseDate(createdAfter), datePattern);
		} catch (DateTimeParseException | NullPointerException ex) {
			return null;
		}
	}
	
	private String parseDate(String dateStr) {
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
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Configuration:\n");
		
		sb.append("\tHelp: ").append(isHelp()).append("\n");
		sb.append("\tVerbose: ").append(isVerbose()).append("\n");
		sb.append("\tSaveDb: ").append(isSaveDb()).append("\n");
		sb.append("\tStationId: ").append(getStationId()).append("\n");
		sb.append("\tOperatorStationId: ").append(getOperatorStationId()).append("\n");
		sb.append("\tExaminationTypeSc: ").append(Arrays.toString(getExaminationTypeSc())).append("\n");
		sb.append("\tParameterSc: ").append(getParameterSc()).append("\n");
		sb.append("\tMeasurementPointNumber: ").append(getMeasurementPointNumber()).append("\n");
		sb.append("\tWithResultsAfter: ").append(getWithResultsAfter()).append("\n");
		sb.append("\tWithResultsCreatedAfter: ").append(getWithResultsCreatedAfter()).append("\n");
		sb.append("\tFrom: ").append(getFrom()).append("\n");
		sb.append("\tTo: ").append(getTo()).append("\n");
		sb.append("\tCreatedAfter: ").append(getCreatedAfter()).append("\n");
		
		return sb.toString();
	}
	
}
