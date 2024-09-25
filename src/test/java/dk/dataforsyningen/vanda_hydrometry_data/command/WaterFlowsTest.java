package dk.dataforsyningen.vanda_hydrometry_data.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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
	private static String date1 = "2024-09-25T09:15:00.00Z";
	private static String date2 = "2024-09-25T08:45:00.00Z";
	private static String date3 = "2024-09-25T06:00:00.00Z";
	private static String date4 = "2024-09-25T05:50:00.00Z";
	
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
		result1.setMeasurementDateTime(VandaHUtility.parseUtcDate(date2));
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
		result1.setMeasurementDateTime(VandaHUtility.parseUtcDate(date4));
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
		
		//TODO assertions
		
		ArrayList<MeasurementType> measurementTypes = cmd.getMeasurementTypes();
		
		assertNotNull(measurementTypes);
		assertEquals(2, measurementTypes.size());
		
		MeasurementType mt1 = measurementTypes.get(0);
		MeasurementType mt2 = measurementTypes.get(1);
		
		//TODO assertions
	}
}
