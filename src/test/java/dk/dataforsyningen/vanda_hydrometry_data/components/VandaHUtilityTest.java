package dk.dataforsyningen.vanda_hydrometry_data.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class VandaHUtilityTest {

	private final String jsonData = """
  {
    "stationUid": "e708d071-85f3-4236-aa92-e1d3944651ee",
    "stationId": "53000012",
    "operatorStationId": null,
    "oldStationNumber": "53000618",
    "locationType": "Vandløb",
    "locationTypeSc": 1,
    "stationOwnerCvr": "DK25798376",
    "stationOwnerName": "Miljøstyrelsen",
    "operatorCvr": null,
    "operatorName": null,
    "name": "Skensved Å, Øst For Lille Skensved",
    "dguNumber": null,
    "description": "Opland = 28 km2",
    "loggerId": "4631593"
    }
			""";
	
	private final String dateString = "2024-10-09T10:11:12.123Z";
	private final long nrMilliseconds = 1728468672000l; //"2024-10-09T10:11:12.000Z"
	
	
	@Test
	public void testValueFromJson() {
		String stationId = VandaHUtility.valueFromJson(jsonData, "stationId");
		assertEquals("53000012", stationId);
	}
	
	@Test
	public void testParseForApi() {
		OffsetDateTime date = VandaHUtility.parseForAPI("2024-10-23T23:30:13.562469701+02:00");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(23, date.getDayOfMonth());
		assertEquals(0, date.getSecond());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseForAPI("2024-10-09T10:11:12.123Z");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(9, date.getDayOfMonth());
		assertEquals(10, date.getHour());
		assertEquals(11, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseForAPI("2024-10-09 10:11:12.123Z");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(9, date.getDayOfMonth());
		assertEquals(10, date.getHour());
		assertEquals(11, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseForAPI("2024-10-09 10:11Z");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(9, date.getDayOfMonth());
		assertEquals(10, date.getHour());
		assertEquals(11, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseForAPI("2024-10-09Z");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(9, date.getDayOfMonth());
		assertEquals(0, date.getHour());
		assertEquals(0, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		OffsetDateTime now = OffsetDateTime.now();
		
		date = VandaHUtility.parseForAPI("2024-10-09T10:12");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		if (ZoneOffset.UTC.equals(now.getOffset())) {
			assertEquals(9, date.getDayOfMonth());
			assertEquals(10, date.getHour());
		} else {
			assertFalse(10 == date.getHour());
		}
		assertEquals(12, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseForAPI("2024-10-9");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		if (ZoneOffset.UTC.equals(now.getOffset())) {
			assertEquals(9, date.getDayOfMonth());
			assertEquals(0, date.getHour());
		}
		assertEquals(0, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(ZoneOffset.UTC, date.getOffset());	
	}
	
	@Test
	public void testParseUtcOffsetDateTime() {
		OffsetDateTime date = VandaHUtility.parseToUtcOffsetDateTime("2024-10-09T10:11:12.123Z");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(9, date.getDayOfMonth());
		assertEquals(10, date.getHour());
		assertEquals(11, date.getMinute());
		assertEquals(12, date.getSecond());
		assertEquals(123000000, date.getNano());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseToUtcOffsetDateTime("2024-10-09 10:11:12.123Z");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(9, date.getDayOfMonth());
		assertEquals(10, date.getHour());
		assertEquals(11, date.getMinute());
		assertEquals(12, date.getSecond());
		assertEquals(123000000, date.getNano());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseToUtcOffsetDateTime("2024-10-09 10:11Z");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(9, date.getDayOfMonth());
		assertEquals(10, date.getHour());
		assertEquals(11, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(0, date.getNano());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseToUtcOffsetDateTime("2024-10-09Z");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		assertEquals(9, date.getDayOfMonth());
		assertEquals(0, date.getHour());
		assertEquals(0, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(0, date.getNano());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		OffsetDateTime now = OffsetDateTime.now();
		
		date = VandaHUtility.parseToUtcOffsetDateTime("2024-10-09T10:12");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		if (ZoneOffset.UTC.equals(now.getOffset())) {
			assertEquals(9, date.getDayOfMonth());
			assertEquals(10, date.getHour());
		} else {
			assertFalse(10 == date.getHour());
		}
		assertEquals(12, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(0, date.getNano());
		assertEquals(ZoneOffset.UTC, date.getOffset());
		
		date = VandaHUtility.parseToUtcOffsetDateTime("2024-10-9");
		assertEquals(2024, date.getYear());
		assertEquals(10, date.getMonthValue());
		if (ZoneOffset.UTC.equals(now.getOffset())) {
			assertEquals(9, date.getDayOfMonth());
			assertEquals(0, date.getHour());
		}
		assertEquals(0, date.getMinute());
		assertEquals(0, date.getSecond());
		assertEquals(0, date.getNano());
		assertEquals(ZoneOffset.UTC, date.getOffset());
	}
	
	@Test
	public void testParseUtcDate() {
		
		OffsetDateTime odt = OffsetDateTime.parse(dateString);
		
		Date date = VandaHUtility.parseUtcDate(dateString);
		assertEquals(odt.toInstant().getEpochSecond(), (int)(date.getTime()/1000));
	}
	
	@Test
	public void testDateToOfssetDateTimeUtc() {
		Date date = new Date(nrMilliseconds);
		
		OffsetDateTime odt = VandaHUtility.dateToOfssetDateTimeUtc(date);
		
		assertEquals(2024, odt.getYear());
		assertEquals(10, odt.getMonthValue());
		assertEquals(9, odt.getDayOfMonth());
		assertEquals(10, odt.getHour());
		assertEquals(11, odt.getMinute());
		assertEquals(12, odt.getSecond());
		assertEquals(0, odt.getNano());
		assertEquals(ZoneOffset.UTC, odt.getOffset());
	}
	
	@Test
	public void dateToOfssetDateTimeLocalZone() {
		
		OffsetDateTime now = OffsetDateTime.now();
		
		Date date = new Date(now.toInstant().getEpochSecond() * 1000l);
		
		OffsetDateTime odt = VandaHUtility.dateToOfssetDateTimeLocalZone(date);
		
		assertEquals(now.getYear(), odt.getYear());
		assertEquals(now.getMonthValue(), odt.getMonthValue());
		assertEquals(now.getDayOfMonth(), odt.getDayOfMonth());
		assertEquals(now.getHour(), odt.getHour());
		assertEquals(now.getMinute(), odt.getMinute());
		assertEquals(now.getSecond(), odt.getSecond());
		assertEquals(0, odt.getNano());
		assertEquals(now.getOffset(), odt.getOffset());
	}
}

