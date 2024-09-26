package dk.dataforsyningen.vanda_hydrometry_data.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.service.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesMeasurementResultResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesResultResponse;

@SpringBootTest
public class WaterFlowsTest {

	private DmpHydroApiResponsesMeasurementResultResponse[] data;
	
	private static String id1 = "10000001";
	private static String opId1 = "OPSTID-001";
	private static String id2 = "10000002";
	private static String opId2 = "OPSTID-002";
	private static int mp1 = 1;
	private static int mp2 = 2;
	
	//stand
	private static int paramSc1 = 1233;
	private static String param1 = "Vandstand";
	private static int examTSc1 = 25;
	private static String examT1 = "Vandstand";
	private static int unitSc1 = 19;
	private static String unit1 = "cm";
	
	//flow
	private static int paramSc2 = 1155;
	private static String param2 = "Vandføring";
	private static int examTSc2 = 27;
	private static String examT2 = "Vandføring";
	private static int unitSc2 = 55;
	private static String unit2 = "l/s";

	private static double res1 = 1.2;
	private static double res2 = 2.3;
	private static double res3 = 4.5;
	private static double res4 = 6.7;
	private static String date1 = "2024-09-25T09:15:00.001Z";
	private static String date2 = "2024-09-25T08:45:00.001Z";
	private static String date3 = "2024-09-25T06:00:00.001Z";
	private static String date4 = "2024-09-25T05:50:00.001Z";
	
	@Mock
	private VandaHydrometryDataConfig config;
	
	@Mock
	private VandahDmpApiService vandahService;
	
	@InjectMocks
	private WaterFlowsCommand cmd = new WaterFlowsCommand(); 
	
	@BeforeEach
	public void setUp() {
		data = new DmpHydroApiResponsesMeasurementResultResponse[2];
		
		DmpHydroApiResponsesMeasurementResultResponse measurementResult1 = new DmpHydroApiResponsesMeasurementResultResponse();
		measurementResult1.setStationId(id1);
		measurementResult1.setOperatorStationId(opId1);
		
		ArrayList<DmpHydroApiResponsesResultResponse> results = new ArrayList<>();
		
		DmpHydroApiResponsesResultResponse result1 = new DmpHydroApiResponsesResultResponse();
		result1.setMeasurementPointNumber(mp1);
		result1.setParameterSc(paramSc2);
		result1.setParameter(param2);
		result1.setExaminationTypeSc(examTSc2);
		result1.setExaminationType(examT2);
		result1.setUnitSc(unitSc2);
		result1.setUnit(unit2);
		result1.setResult(res1);
		result1.setMeasurementDateTime(VandaHUtility.parseUtcDate(date1));
		results.add(result1);
		
		DmpHydroApiResponsesResultResponse result2 = new DmpHydroApiResponsesResultResponse();
		result2.setMeasurementPointNumber(mp2);
		result2.setParameterSc(paramSc2);
		result2.setParameter(param2);
		result2.setExaminationTypeSc(examTSc2);
		result2.setExaminationType(examT2);
		result2.setUnitSc(unitSc2);
		result2.setUnit(unit2);
		result2.setResult(res2);
		result2.setMeasurementDateTime(VandaHUtility.parseUtcDate(date2));
		results.add(result2);
		
		measurementResult1.setResults(results);
		
		
		DmpHydroApiResponsesMeasurementResultResponse measurementResult2 = new DmpHydroApiResponsesMeasurementResultResponse();
		measurementResult2.setStationId(id2);
		measurementResult2.setOperatorStationId(opId2);
		
		results = new ArrayList<>();
			
		result1 = new DmpHydroApiResponsesResultResponse();
		result1.setMeasurementPointNumber(mp1);
		result1.setParameterSc(paramSc2);
		result1.setParameter(param2);
		result1.setExaminationTypeSc(examTSc2);
		result1.setExaminationType(examT2);
		result1.setUnitSc(unitSc2);
		result1.setUnit(unit2);
		result1.setResult(res3);
		result1.setMeasurementDateTime(VandaHUtility.parseUtcDate(date3));
		results.add(result1);
			
		result2 = new DmpHydroApiResponsesResultResponse();	
		result2.setMeasurementPointNumber(mp2);
		result2.setParameterSc(paramSc1);
		result2.setParameter(param1);
		result2.setExaminationTypeSc(examTSc1);
		result2.setExaminationType(examT1);
		result2.setUnitSc(unitSc1);
		result2.setUnit(unit1);
		result2.setResult(res4);
		result2.setMeasurementDateTime(VandaHUtility.parseUtcDate(date4));
		results.add(result2);
			
		measurementResult2.setResults(results);
		
		data[0] = measurementResult1;
		data[1] = measurementResult2;
		
		
		when(config.getStationId()).thenReturn(null);
		when(config.getOperatorStationId()).thenReturn(null);
		when(config.getMeasurementPointNumber()).thenReturn(null);
		when(config.getFrom()).thenReturn(null);
		when(config.getTo()).thenReturn(null);
		when(config.getCreatedAfter()).thenReturn(null);
		
		when(vandahService.getWaterFlows(any(),any(),any(),any(),any(),any(),any())).thenReturn(data);
	}
	
	@Test
	public void testDataMapping() {
		cmd.getData(); //read mock data
		
		cmd.mapData();
		
		ArrayList<Measurement> measurements = cmd.getMeasurements();

		assertNotNull(measurements);
		assertEquals(4, measurements.size());
		
		Measurement m1 = measurements.get(0);
		Measurement m2 = measurements.get(1);
		Measurement m3 = measurements.get(2);
		Measurement m4 = measurements.get(3);
		
		assertEquals(mp1, m1.getMeasurementPointNumber());
		assertEquals(id1, m1.getStationId());
		assertEquals(date1, "" + m1.getMeasurementDateTime());
		assertEquals(res1, m1.getResult());
		
		assertEquals(mp2, m2.getMeasurementPointNumber());
		assertEquals(id1, m2.getStationId());
		assertEquals(date2, "" + m2.getMeasurementDateTime());
		assertEquals(res2, m2.getResult());
		
		assertEquals(mp1, m3.getMeasurementPointNumber());
		assertEquals(id2, m3.getStationId());
		assertEquals(date3, "" + m3.getMeasurementDateTime());
		assertEquals(res3, m3.getResult());
		
		assertEquals(mp2, m4.getMeasurementPointNumber());
		assertEquals(id2, m4.getStationId());
		assertEquals(date4, "" + m4.getMeasurementDateTime());
		assertEquals(res4, m4.getResult());
		
		ArrayList<MeasurementType> measurementTypes = cmd.getMeasurementTypes();
		
		assertNotNull(measurementTypes);
		assertEquals(2, measurementTypes.size());
		
		MeasurementType mt1 = measurementTypes.get(0);
		MeasurementType mt2 = measurementTypes.get(1);
		
		assertEquals(param2, mt1.getParameter());
		assertEquals(paramSc2, mt1.getParameterSc());
		assertEquals(examT2, mt1.getExaminationType());
		assertEquals(examTSc2, mt1.getExaminationTypeSc());
		assertEquals(unit2, mt1.getUnit());
		assertEquals(unitSc2, mt1.getUnitSc());
		
		assertEquals(param1, mt2.getParameter());
		assertEquals(paramSc1, mt2.getParameterSc());
		assertEquals(examT1, mt2.getExaminationType());
		assertEquals(examTSc1, mt2.getExaminationTypeSc());
		assertEquals(unit1, mt2.getUnit());
		assertEquals(unitSc1, mt2.getUnitSc());
	}
	
	
	//TODO: remove
	/**
	 * 2024-09-26T10:15:00.00Z parsed into LDT: 2024-09-26T10:15	OK but missing TZ
2024-09-26T10:15:00.00Z parsed into ODT: 2024-09-26T10:15Z	OK
2024-09-26T10:15:00.00Z parsed into LDT and converted to date (current zone): Thu Sep 26 10:15:00 CEST 2024 	OK display but WRONG due to TZ
2024-09-26T10:15:00.00Z parsed into LDT and converted to date (utc zone): Thu Sep 26 12:15:00 CEST 2024		OK but WRONG display
2024-09-26T10:15:00.00Z parsed into ODT and converted to date: Thu Sep 26 12:15:00 CEST 2024			OK but WRONG display
LocalDateTime of NOW Thu Sep 26 13:03:24 CEST 2024 in UTC: 2024-09-26T11:03:24.927	OK
OffsetDateTime of NOW Thu Sep 26 13:03:24 CEST 2024 in UTC: 2024-09-26T11:03:24.927Z	OK

2024-09-26T10:15:00.00 parsed into LDT: 2024-09-26T10:15
2024-09-26T10:15:00.00 parsed into ODT: FAIL
2024-09-26T10:15:00.00 parsed into LDT and converted to date (current zone): Thu Sep 26 10:15:00 CEST 2024	OK
2024-09-26T10:15:00.00 parsed into LDT and converted to date (utc zone): Thu Sep 26 12:15:00 CEST 2024		WRONG TZ
2024-09-26T10:15:00.00 parsed into ODT and converted to date: FAIL
2024-09-26T10:15:01.01 parsed into LDT and converted to ODT (UTC zone): 2024-09-26T10:15:01.010Z		WRONG
2024-09-26T10:15:01.01 parsed into LDT and converted to ODT (current zone): 2024-09-26T10:15:01.010+02:00	OK
2024-09-26T10:15 parsed into LDT and converted to ODT (current zone): 2024-09-26T10:15+02:00 displayed in UTC: 2024-09-26T08:15Z

	 * @throws ParseException
	 */
	public void testDT() throws ParseException {
		String dateStr = "2024-09-26T10:15Z";
		
		LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
		System.out.println(dateStr + " parsed into LDT: " + ldt);
		
		OffsetDateTime odt = OffsetDateTime.parse(dateStr);
		System.out.println(dateStr + " parsed into ODT: " + odt);
		
		Date d1 = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		System.out.println(dateStr + " parsed into LDT and converted to date (current zone): " + d1);
		
		Date d3 = Date.from(ldt.atZone(ZoneId.of("UTC")).toInstant());
		System.out.println(dateStr + " parsed into LDT and converted to date (utc zone): " + d3);
		
		Date d2 = Date.from(odt.toInstant());
		System.out.println(dateStr + " parsed into ODT and converted to date: " + d2);
		
		
		Date date = new Date();
        Instant instant = date.toInstant();
        
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        System.out.println("LocalDateTime of NOW " + date + " in UTC: " + localDateTime);
        
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);
        System.out.println("OffsetDateTime of NOW " + date + " in UTC: " + offsetDateTime);
        
        
        
        dateStr = "2024-09-26T10:15";
		
		ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
		System.out.println(dateStr + " parsed into LDT: " + ldt);
				
		d1 = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		System.out.println(dateStr + " parsed into LDT and converted to date (current zone): " + d1);
		
		d3 = Date.from(ldt.atZone(ZoneId.of("UTC")).toInstant());
		System.out.println(dateStr + " parsed into LDT and converted to date (utc zone): " + d3);		
		        
		odt = ldt.atOffset(ZoneOffset.UTC);
		System.out.println(dateStr + " parsed into LDT and converted to ODT (UTC zone): " + odt);
		
		odt = ldt.atOffset(ZoneId.systemDefault().getRules().getOffset(ldt));
		System.out.println(dateStr + " parsed into LDT and converted to ODT (current zone): " + odt + " displayed in UTC: " + odt.withOffsetSameInstant(ZoneOffset.UTC));
		
		
		/*
		String normalized = VandaHUtility.normalizeDate(dateStr, false);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ", Locale.ENGLISH);
		
		Date d = formatter.parse(dateStr);
		
		System.out.println(dateStr + " parsed into " + d);
		
		Date d1 = formatter.parse(normalized);
		
		System.out.println(normalized + " parsed into " + d1);
		*/
	}
}
