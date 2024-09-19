package dk.dataforsyningen.vanda_hydrometry_data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

/**
 * Testing of parsing arguments as it would be given by the user in command line.
 * Using Nested classes to test different properties scenarios
 */
@SpringBootTest
public class VandaHydrometryDataConfigTest {
	
	/**
	 * Test reading options without value and missing options.
	 * Test case insensitiveness.
	 * Test parsing values.
	 * Test parsing arrays of values.
	 */
	@Nested
	@ContextConfiguration
	@TestPropertySource(
			properties = {"help", "VERBoSE", "stationid=99999999", "parameterSc=1234", "examinationtypesc=25,27"}
			)
	public class VandaHydrometryDataConfigTest1 {
		
		@Autowired
		private VandaHydrometryDataConfig config;
	
		@Test
		public void testParams() {
			assertTrue(config.isHelp());
			assertTrue(config.isVerbose());
			assertFalse(config.isSaveDb());
			assertEquals("99999999", config.getStationId());
			assertEquals(1234, config.getParameterSc());
			assertEquals(2,config.getExaminationTypeSc().length);
			assertEquals(25,config.getExaminationTypeSc()[0]);
			assertEquals(27,config.getExaminationTypeSc()[1]);
		}
	}
	
	/**
	 * Test parsing invalid values.
	 * Test parsing arrays of values skipping invalid values.
	 */
	@Nested
	@ContextConfiguration
	@TestPropertySource(
			properties = {"stationid=abcd9999", "parametersc=12ab", "examinationtypesc=25,a,27"}
			)
	public class VandaHydrometryDataConfigTest2 {
		
		@Autowired
		private VandaHydrometryDataConfig config;
	
		@Test
		public void testInvalidOptions() {
			assertEquals("abcd9999", config.getStationId());
			assertNull(config.getParameterSc());
			assertEquals(2,config.getExaminationTypeSc().length);
			assertEquals(25,config.getExaminationTypeSc()[0]);
			assertEquals(27,config.getExaminationTypeSc()[1]);
		}
	}
	
	
	/**
	 * Test missing options (with values).
	 * Test parsing single value as array.
	 */
	@Nested
	@ContextConfiguration
	@TestPropertySource(
			properties = {"examinationtypesc=25"}
			)
	public class VandaHydrometryDataConfigTest3 {
		
		@Autowired
		private VandaHydrometryDataConfig config;
	
		@Test
		public void testOptions() {
			assertNull(config.getStationId());
			assertNull(config.getParameterSc());
			assertEquals(1,config.getExaminationTypeSc().length);
			assertEquals(25,config.getExaminationTypeSc()[0]);
		}
	}
	
	/**
	 * Test date parsing with different formats
	 */
	@Nested
	@ContextConfiguration
	@TestPropertySource(
			properties = {"withResultsAfter=2024-09-01", 
					"withResultsCreatedAfter=2024-09-01T23:24Z",
					"from=2024-09-01T23:24:30",
					"to=2024-09-01 23:24",
					"createdAfter=2024-09-01 12:34:56"
					}
			)
	public class VandaHydrometryDataConfigTest4 {
		
		@Autowired
		private VandaHydrometryDataConfig config;
	
		@Test
		public void testDates() {
			assertDoesNotThrow(() -> config.getWithResultsAfter());
			assertEquals("2024-09-01T00:00", "" + config.getWithResultsAfter());
			
			assertDoesNotThrow(() -> config.getWithResultsCreatedAfter());
			assertEquals("2024-09-01T23:24", "" + config.getWithResultsCreatedAfter());
			
			assertDoesNotThrow(() -> config.getFrom());
			assertEquals("2024-09-01T23:24", "" + config.getFrom());
			
			assertDoesNotThrow(() -> config.getTo());
			assertEquals("2024-09-01T23:24", "" + config.getTo());
			
			assertDoesNotThrow(() -> config.getCreatedAfter());
			assertEquals("2024-09-01T12:34", "" + config.getCreatedAfter());
		}
	}
	
	/**
	 * Test invalid date parsing
	 */
	@Nested
	@ContextConfiguration
	@TestPropertySource(
			properties = {"withResultsAfter=2024-99-01", 
					"withResultsCreatedAfter=2024-09-01T23:74Z",
					"from=2024-09-01T23:24:30Y",
					"to=1234",
					"createdAfter=asdf"
					}
			)
	public class VandaHydrometryDataConfigTest5 {
		
		@Autowired
		private VandaHydrometryDataConfig config;
	
		@Test
		public void testInvalidDates() {
			assertNull(config.getWithResultsAfter());
			
			assertNull(config.getWithResultsCreatedAfter());
			
			assertNull(config.getFrom());
			
			assertNull(config.getTo());
			
			assertNull(config.getCreatedAfter());
		}
	}
}
