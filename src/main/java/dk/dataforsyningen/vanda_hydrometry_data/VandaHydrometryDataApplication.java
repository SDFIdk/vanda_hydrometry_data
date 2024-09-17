package dk.dataforsyningen.vanda_hydrometry_data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;

/**
 * The main startup class of the application.
 * 
 * @author Radu Dudici
 */
@SpringBootApplication
public class VandaHydrometryDataApplication {
	
	private static final Logger log = LoggerFactory.getLogger(VandaHydrometryDataApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(VandaHydrometryDataApplication.class, args);
	}
	
	@Bean
	public RestClient restClient() {
		return RestClient.create();
	}

}
