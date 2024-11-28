package dk.dataforsyningen.vanda_hydrometry_data.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;

public class ConfigDateTimeParseTest {



  /**
   * With timezone
   */
  @Test
  public void testParseDateTimeWithTZ() {
	String s = "2024-11-28T12:30:01.123456789+01:00";
    OffsetDateTime date = VandaHydrometryDataConfig.parseForAPI(s, false);
    ////System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(123456789, date.getNano());
    assertEquals(ZoneOffset.ofHours(1), date.getOffset());

    s = "2024-11-28T12:30:01.123+01:00";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    ////System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(123000000, date.getNano());
    assertEquals(ZoneOffset.ofHours(1), date.getOffset());
    
    
    s = "2024-11-28T12:30:01+01:00";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.ofHours(1), date.getOffset());

    s = "2024-11-28T12:30+01:00";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(0, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.ofHours(1), date.getOffset());
    
    s = "2024-11-28+01:00";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(0, date.getHour());
    assertEquals(0, date.getMinute());
    assertEquals(0, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.ofHours(1), date.getOffset());
  }

  /**
   * Zulu timezone
   */
  @Test
  public void testParseDateTimeZulu() {
    String s = "2024-11-28T12:30:01.123456789Z";
    OffsetDateTime date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(123456789, date.getNano());
    assertEquals(ZoneOffset.UTC, date.getOffset());

    s = "2024-11-28T12:30:01.123Z";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(123000000, date.getNano());
    assertEquals(ZoneOffset.UTC, date.getOffset());
    
    
    s = "2024-11-28T12:30:01Z";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.UTC, date.getOffset());

    s = "2024-11-28T12:30Z";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(0, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.UTC, date.getOffset());
    
    s = "2024-11-28Z";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(0, date.getHour());
    assertEquals(0, date.getMinute());
    assertEquals(0, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.UTC, date.getOffset());
  }
    
  /**
   * without timezone, consider system's timezone
   */
  @Test
  public void testParseDateTimeWithoutTZ() { 
    String s = "2024-11-28T12:30:01.123456789";
    OffsetDateTime date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(123456789, date.getNano());
    assertEquals(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()), date.getOffset());

    s = "2024-11-28T12:30:01.123";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(123000000, date.getNano());
    assertEquals(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()), date.getOffset());
    
    
    s = "2024-11-28T12:30:01";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(01, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()), date.getOffset());

    s = "2024-11-28T12:30";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(12, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(0, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()), date.getOffset());
    
    s = "2024-11-28";
    date = VandaHydrometryDataConfig.parseForAPI(s, false);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(0, date.getHour());
    assertEquals(0, date.getMinute());
    assertEquals(0, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()), date.getOffset());
  }

  @Test
  public void testToUtc() {
	String s = "2024-11-28T12:30";
	OffsetDateTime date = VandaHydrometryDataConfig.parseForAPI(s, true);
	//System.out.println(s + " -> " + date);
	assertEquals(2024, date.getYear());
	assertEquals(11, date.getMonthValue());
	assertEquals(28, date.getDayOfMonth());
	assertEquals(11, date.getHour());
	assertEquals(30, date.getMinute());
	assertEquals(0, date.getSecond());
	assertEquals(0, date.getNano());
	assertEquals(ZoneOffset.UTC, date.getOffset());
	
	s = "2024-11-28T12:30+01:00";
    date = VandaHydrometryDataConfig.parseForAPI(s, true);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(28, date.getDayOfMonth());
    assertEquals(11, date.getHour());
    assertEquals(30, date.getMinute());
    assertEquals(0, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.UTC, date.getOffset());
    
    s = "2024-11-28+01:00";
    date = VandaHydrometryDataConfig.parseForAPI(s, true);
    //System.out.println(s + " -> " + date);
    assertEquals(2024, date.getYear());
    assertEquals(11, date.getMonthValue());
    assertEquals(27, date.getDayOfMonth());
    assertEquals(23, date.getHour());
    assertEquals(0, date.getMinute());
    assertEquals(0, date.getSecond());
    assertEquals(0, date.getNano());
    assertEquals(ZoneOffset.UTC, date.getOffset());
  }

}

