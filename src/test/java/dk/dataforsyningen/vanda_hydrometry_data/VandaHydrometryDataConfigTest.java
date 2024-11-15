package dk.dataforsyningen.vanda_hydrometry_data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
   */
  @Nested
  @ContextConfiguration
  @TestPropertySource(
      properties = {"help=", "stationid=99999999", "parameterSc=1234", "examinationtypesc=25"}
  )
  public class VandaHydrometryDataConfigTest1 {

    @Autowired
    private VandaHydrometryDataConfig config;

    @Test
    public void testParams() {
      assertTrue(config.isHelp());
      assertFalse(config.isSaveDb());
      assertEquals("99999999", config.getStationId());
      assertEquals(1234, config.getParameterSc());
      assertEquals(25, config.getExaminationTypeSc());
    }
  }

  /**
   * Test parsing invalid values.
   * Test parsing arrays of values skipping invalid values.
   */
  @Nested
  @ContextConfiguration
  @TestPropertySource(
      properties = {"stationid=abcd9999", "parametersc=12ab", "examinationtypesc="}
  )
  public class VandaHydrometryDataConfigTest2 {

    @Autowired
    private VandaHydrometryDataConfig config;

    @Test
    public void testInvalidOptions() {
      assertEquals("abcd9999", config.getStationId());
      assertThrows(NumberFormatException.class, () -> config.getParameterSc());
      assertThrows(NumberFormatException.class, () -> config.getExaminationTypeSc());
    }
  }


  /**
   * Test missing options (with values) and comma separated values.
   */
  @Nested
  @ContextConfiguration
  @TestPropertySource(
      properties = {"stationId=10000001,10000002"}
  )
  public class VandaHydrometryDataConfigTest3 {

    @Autowired
    private VandaHydrometryDataConfig config;

    @Test
    public void testOptions() {
      assertEquals("10000001,10000002", config.getStationId());
      assertNull(config.getParameterSc());
      assertNull(config.getExaminationTypeSc());
    }
  }

  /**
   * Test date parsing with different formats in UTC (Zulu)
   */
  @Nested
  @ContextConfiguration
  @TestPropertySource(
      properties = {"withResultsAfter=2024-09-01z",
          "withResultsCreatedAfter=2024-09-01T23:24:02.000Z",
          "from=2024-09-01T23:24Z",
          "to=2024-09-01 23:24Z",
          "createdAfter=2024-09-01 12:34:56z"
      }
  )
  public class VandaHydrometryDataConfigTest4 {

    @Autowired
    private VandaHydrometryDataConfig config;

    @Test
    public void testDatesUTC() {
      assertDoesNotThrow(() -> config.getWithResultsAfter());
      assertEquals("2024-09-01T00:00Z", "" + config.getWithResultsAfter());

      assertDoesNotThrow(() -> config.getWithResultsCreatedAfter());
      assertEquals("2024-09-01T23:24Z", "" + config.getWithResultsCreatedAfter());

      assertDoesNotThrow(() -> config.getFrom());
      assertEquals("2024-09-01T23:24Z", "" + config.getFrom());

      assertDoesNotThrow(() -> config.getTo());
      assertEquals("2024-09-01T23:24Z", "" + config.getTo());

      assertDoesNotThrow(() -> config.getCreatedAfter());
      assertEquals("2024-09-01T12:34Z", "" + config.getCreatedAfter());
    }
  }


  /**
   * Test date parsing with different formats in local time zone
   */
  @Nested
  @ContextConfiguration
  @TestPropertySource(
      properties = {"withResultsAfter=2024-09-01",
          "withResultsCreatedAfter=2024-09-01T23:24:02.001",
          "from=2024-09-01T23:24",
          "to=2024-09-01 23:24",
          "createdAfter=2024-09-01 12:34:56.123"
      }
  )
  public class VandaHydrometryDataConfigTest5 {

    @Autowired
    private VandaHydrometryDataConfig config;

    @Test
    public void testDatesLocalTimeZone() {
      LocalDateTime ldt = LocalDateTime.of(2024, 9, 01, 0, 0, 0, 0);
      OffsetDateTime odt = ldt.atZone(ZoneId.systemDefault()).toOffsetDateTime()
          .withOffsetSameInstant(ZoneOffset.UTC);
      assertDoesNotThrow(() -> config.getWithResultsAfter());
      assertEquals("" + odt, "" + config.getWithResultsAfter());

      ldt = LocalDateTime.of(2024, 9, 1, 23, 24, 0, 0);
      odt = ldt.atZone(ZoneId.systemDefault()).toOffsetDateTime()
          .withOffsetSameInstant(ZoneOffset.UTC);
      assertDoesNotThrow(() -> config.getWithResultsCreatedAfter());
      assertEquals("" + odt, "" + config.getWithResultsCreatedAfter());

      ldt = LocalDateTime.of(2024, 9, 1, 23, 24, 0, 0);
      odt = ldt.atZone(ZoneId.systemDefault()).toOffsetDateTime()
          .withOffsetSameInstant(ZoneOffset.UTC);
      assertDoesNotThrow(() -> config.getFrom());
      assertEquals("" + odt, "" + config.getFrom());

      ldt = LocalDateTime.of(2024, 9, 1, 23, 24, 0, 0);
      odt = ldt.atZone(ZoneId.systemDefault()).toOffsetDateTime()
          .withOffsetSameInstant(ZoneOffset.UTC);
      assertDoesNotThrow(() -> config.getTo());
      assertEquals("" + odt, "" + config.getTo());

      ldt = LocalDateTime.of(2024, 9, 1, 12, 34, 0, 0);
      odt = ldt.atZone(ZoneId.systemDefault()).toOffsetDateTime()
          .withOffsetSameInstant(ZoneOffset.UTC);
      assertDoesNotThrow(() -> config.getCreatedAfter());
      assertEquals("" + odt, "" + config.getCreatedAfter());
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
  public class VandaHydrometryDataConfigTest6 {

    @Autowired
    private VandaHydrometryDataConfig config;

    @Test
    public void testInvalidDates() {
      assertThrows(InvalidParameterException.class, () -> config.getWithResultsAfter());

      assertThrows(InvalidParameterException.class, () -> config.getWithResultsCreatedAfter());

      assertThrows(InvalidParameterException.class, () -> config.getFrom());

      assertThrows(InvalidParameterException.class, () -> config.getTo());

      assertThrows(InvalidParameterException.class, () -> config.getCreatedAfter());
    }
  }
}
