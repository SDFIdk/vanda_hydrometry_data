package dk.dataforsyningen.vanda_hydrometry_data;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.ToString;

/**
 * Configuration class.
 * Reads and provides the config from properties file.
 * 
 * @author Radu Dudici
 */
@Configuration
public class VandaHydrometryDataConfig {

	/* Values from the properties file */
	
	@Value("${dmp.vandah.api.url:#{null}}") //use "${dmp.vandah.api.url:#{null}}" if you need default to null if missing
	@Getter String vandahDmpApiUrl;
	
	/* Option values from the command line */
	
	/*public enum Options {
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
	}*/

	@Value("${help:#{null}}")
	private String help;
	
	@Value("${verbose:#{null}}")
	private String verbose;
	
	@Value("${savedb:#{null}}")
	private String saveDb;
	
	@Value("${stationid:#{null}}")
	@Getter private Integer stationId;
	
	@Value("${examinationtypesc:#{null}}")
	private String examinationTypeSc;
	
	
	
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Configuration:\n");
		
		sb.append("\tHelp: ").append(isHelp()).append("\n");
		sb.append("\tVerbose: ").append(isVerbose()).append("\n");
		sb.append("\tSaveDb: ").append(isSaveDb()).append("\n");
		sb.append("\tStationId: ").append(getStationId()).append("\n");
		sb.append("\tExaminationTypeSc: ").append(Arrays.toString(getExaminationTypeSc())).append("\n");
		
		return sb.toString();
	}
	
}
