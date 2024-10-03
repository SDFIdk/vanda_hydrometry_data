package dk.dataforsyningen.vanda_hydrometry_data;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;

/**
 * Configuration class.
 * Reads and provides the configuration from properties file.
 * 
 * @author Radu Dudici
 */
@Configuration
public class VandaHydrometryDataConfig {
	
	private static final Logger log = LoggerFactory.getLogger(VandaHydrometryDataConfig.class);

	/* Values from the properties file */
	
	@Value("${dmp.vandah.api.url:#{null}}")
	String vandahDmpApiUrl;
	
	/* Option values from the command line */
	
	/*
		help,
		savedb,
		verbose,
		display,
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
	
	@Value("${displaydata:#{null}}")
	private String displayData;  //boolean
	
	@Value("${displayrawdata:#{null}}")
	private String displayRawData;  //boolean
	
	@Value("${savedb:#{null}}")
	private String saveDb;  //boolean
	
	@Value("${stationid:#{null}}")
	private String stationId;
	
	@Value("${examinationtypesc:#{null}}")
	private String examinationTypeSc;  //int[]
	
	@Value("${parametersc:#{null}}")
	private String parameterSc; //Integer
	
	@Value("${operatorstationid:#{null}}")
	private String operatorStationId;
	
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
	
	public boolean isDisplayData() {
		return displayData != null;
	}
	
	public boolean isDisplayRawData() {
		return displayRawData != null;
	}
	
	public boolean isSaveDb() {
		return saveDb != null;
	}
	
	public Integer[] getExaminationTypeSc() {
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
							}).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
		}
		try {
			return new Integer[] {Integer.parseInt(examinationTypeSc)};
		} catch (NumberFormatException e) {
			VandaHUtility.logAndPrint(log, Level.WARN, isVerbose(), "Invalid number found in 'examinationTypeSc' parameter.");
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
	
	public OffsetDateTime getWithResultsAfter() {
				
		if (withResultsAfter == null) return null;
		try {
			return VandaHUtility.parseForAPI(withResultsAfter);
		} catch (DateTimeParseException | NullPointerException ex) {
			VandaHUtility.logAndPrint(log, Level.WARN, isVerbose(), "Invalid date format found in 'withResultsAfter' parameter.");
			return null;
		}
	}
	
	public OffsetDateTime getWithResultsCreatedAfter() {
		if (withResultsCreatedAfter == null) return null;
		try {
			return VandaHUtility.parseForAPI(withResultsCreatedAfter);
		} catch (DateTimeParseException | NullPointerException ex) {
			VandaHUtility.logAndPrint(log, Level.WARN, isVerbose(), "Invalid date format found in 'withResultsCreatedAfter' parameter.");
			return null;
		}
	}
	
	public OffsetDateTime getFrom() {
		if (from == null) return null;
		try {
			return VandaHUtility.parseForAPI(from);
		} catch (DateTimeParseException | NullPointerException ex) {
			VandaHUtility.logAndPrint(log, Level.WARN, isVerbose(), "Invalid date format found in 'from' parameter.");
			return null;
		}
	}
	
	public OffsetDateTime getTo() {
		if (to == null) return null;
		try {
			return VandaHUtility.parseForAPI(to);
		} catch (DateTimeParseException | NullPointerException ex) {
			VandaHUtility.logAndPrint(log, Level.WARN, isVerbose(), "Invalid date format found in 'to' parameter.");
			return null;
		}
	}
	
	public OffsetDateTime getCreatedAfter() {
		if (createdAfter == null) return null;
		try {
			return VandaHUtility.parseForAPI(createdAfter);
		} catch (DateTimeParseException | NullPointerException ex) {
			VandaHUtility.logAndPrint(log, Level.WARN, isVerbose(), "Invalid date format found in 'createdAfter' parameter.");
			return null;
		}
	}
	
	
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Configuration:\n");
		
		sb.append("\tHelp: ").append(isHelp()).append("\n");
		sb.append("\tVerbose: ").append(isVerbose()).append("\n");
		sb.append("\tDisplayRawData: ").append(isDisplayRawData()).append("\n");
		sb.append("\tDisplayData: ").append(isDisplayData()).append("\n");
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

	public String getVandahDmpApiUrl() {
		return vandahDmpApiUrl;
	}

	public String getStationId() {
		return stationId;
	}

	public String getOperatorStationId() {
		return operatorStationId;
	}
	
}
