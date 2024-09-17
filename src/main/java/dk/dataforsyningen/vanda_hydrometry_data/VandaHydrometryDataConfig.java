package dk.dataforsyningen.vanda_hydrometry_data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class.
 * Reads and provides the config from properties file.
 * 
 * @author Radu Dudici
 */
@Configuration
public class VandaHydrometryDataConfig {

	@Value("${dmp.vandah.api.url:#{null}}") //use "${dmp.vandah.api.url:#{null}}" if you need default to null if missing
	String vandahDmpApiUrl;
	
	public String getVandahDmpApiUrl() {
		return vandahDmpApiUrl;
	}	
}
