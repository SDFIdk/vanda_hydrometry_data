package dk.dataforsyningen.vanda_hydrometry_data.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import dk.dataforsyningen.vanda_hydrometry_data.model.Location;
import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;

/**
 * Test suite to test the DAO and mapping classes and database service.
 * The tests need to be activated from the properties file by:
 * 		vanda-hidrometry-data.database.test=true
 */
@SpringBootTest
public class DatabaseServiceTest {

	private final String stationId = "S1234567";
	private final String stationName = "name";
	private final String stationOwner = "owner";
	private final String stationDescription = "description";
	private final String stationName2 = "Kbh water station";
	private final String stationOwner2 = "Kbh mun.";
	private final String stationDescription2 = "water station in Kbh";
	private final String stationOldNumber = "12345678";
	private final double locationX = 12.34;
	private final double locationY = 56.78;
	
	private final String mtId1 = "T1233-25-19";
	private final int mtParamSc1 = 1233;
	private final String mtParam1 = "WaterLevel";
	private final String mtParam1b = "Vandstand";
	private final int mtExamTypeSc1 = 25;
	private final String mtExamType1 = "WaterLevel";
	private final String mtExamType1b = "Vandstand";
	private final int mtUnitSc1 = 19;
	private final String mtUnit1 = "CM";
	private final String mtUnit1b = "cm";
	
	private final String mtId2 = "T1155-27-55";
	private final int mtParamSc2 = 1155;
	private final String mtParam2 = "Vandføring";
	private final int mtExamTypeSc2 = 27;
	private final String mtExamType2 = "Vandføring";
	private final int mtUnitSc2 = 55;
	private final String mtUnit2 = "l/s";
	
	private final int measurementPoint1 = 1;
	private final int measurementPoint2 = 2;
	private final double result1 = 12.34;
	private final double result1b = 11.99;
	private final double result2 = 56.78;
	
	Station station1;
	Location location;
	MeasurementType mt1;
	MeasurementType mt2;
	Measurement m1;
	Measurement m2;
	OffsetDateTime date1;
	OffsetDateTime date2;
	
	@Value("${vanda-hidrometry-data.database.test:#{false}}")
	public boolean enableTest;
	
	@Autowired
	private DatabaseService dbService;
			
	@BeforeEach
	public void setup() {
		
		System.out.println("Database testing " + (enableTest ? "enabled" : "disabled"));
		if (!enableTest) return;
		
		station1 = new Station();
		station1.setStationId(stationId);
		station1.setName(stationName);
		station1.setStationOwnerName(stationOwner);
		station1.setDescription(stationDescription);
		station1.setOldStationNumber(stationOldNumber);
		location = new Location();
		location.setX(locationX);
		location.setY(locationY);
		station1.setLocation(location);
		
		mt1 = new MeasurementType();
		mt1.setMeasurementTypeId(mtId1);
		mt1.setParameterSc(mtParamSc1);
		mt1.setParameter(mtParam1);
		mt1.setExaminationTypeSc(mtExamTypeSc1);
		mt1.setExaminationType(mtExamType1);
		mt1.setUnitSc(mtUnitSc1);
		mt1.setUnit(mtUnit1);
		
		mt2 = new MeasurementType();
		mt2.setMeasurementTypeId(mtId2);
		mt2.setParameterSc(mtParamSc2);
		mt2.setParameter(mtParam2);
		mt2.setExaminationTypeSc(mtExamTypeSc2);
		mt2.setExaminationType(mtExamType2);
		mt2.setUnitSc(mtUnitSc2);
		mt2.setUnit(mtUnit2);
		
		m1 = new Measurement();
		m1.setStationId(stationId);
		m1.setIsCurrent(true);
		m1.setMeasurementPointNumber(measurementPoint1);
		m1.setResult(result1);
		m1.setMeasurementTypeId(mtId1);
		m1.setMeasurementDateTime(OffsetDateTime.now());
		
		m2 = new Measurement();
		m2.setStationId(stationId);
		m2.setIsCurrent(true);
		m2.setMeasurementPointNumber(measurementPoint2);
		m2.setResult(result2);
		m2.setMeasurementTypeId("fail");
		m2.setMeasurementDateTime(OffsetDateTime.now());
	}
	
	/**
	 * Clean the inserted items
	 */
	@AfterEach
	public void deleteAll() {

		if (!enableTest) return;
		
		testDeleteMeasurement();
		
		testDeleteMeasurementType();
		
		testDeleteStation();
	}
	
	@Test
	public void testDAO() throws InterruptedException {
		
		if (!enableTest) return;
		
		//Test StationDao
		
		testDeleteStation();
		
		testAddStations();
		
		Thread.sleep(1000); //so the update time is later
		
		testUpdateStations();
		
		// MeasurementTypeDao
		
		testDeleteMeasurementType();
		
		testAddMeasurementType();
		
		testUpdateMeasurementType();
		
		// Measurement
		
		testAddMeasurement();
		
		testUpdateMeasurement();
		
	}
	
	private void testDeleteStation() {
		dbService.deleteStation(stationId);
		
		Station station = dbService.getStation(stationId);
		
		assertNull(station); 
	}
	
	private void testAddStations() throws InterruptedException {
		
		int nr1 = dbService.countStations();
		
		dbService.saveStation(station1);
		
		int nr2 = dbService.countStations();
		assertEquals(nr1 + 1, nr2);
		
		Station station = dbService.getStation(stationId);
		//System.out.println("Inserted station: " + station);
		
		assertEquals(stationId, station.getStationId());
		assertEquals(stationName, station.getName());
		assertEquals(stationOwner, station.getStationOwnerName());
		assertEquals(stationOldNumber, station.getOldStationNumber());
		assertEquals(stationDescription, station.getDescription());
		assertEquals(location, station.getLocation());
		
		assertNotNull(station.getCreated());
		assertNotNull(station.getUpdated());
		assertTrue(station.getCreated().equals(station.getUpdated()));
	}
		
	private void testUpdateStations() throws InterruptedException {
		
		int nr1 = dbService.countStations();
		
		station1.setName(stationName2);
		station1.setStationOwnerName(stationOwner2);
		station1.setDescription(stationDescription2);
		dbService.saveStation(station1);

		int nr2 = dbService.countStations();
		assertEquals(nr1, nr2);
		
		Station station = dbService.getStation(stationId);
		//System.out.println("Updated station: " + station);
		
		assertEquals(stationName2, station.getName());
		assertEquals(stationOwner2, station.getStationOwnerName());
		assertEquals(stationDescription2, station.getDescription());
		
		assertNotNull(station.getCreated());
		assertNotNull(station.getUpdated());
		assertTrue(station.getCreated().isBefore(station.getUpdated()));
	}
	
	private void testDeleteMeasurementType() {
		dbService.deleteMeasurementType(mtId1);
		
		MeasurementType mt = dbService.getMeasurementType(mtId1);
		assertNull(mt);
		
		dbService.deleteMeasurementType(mtId2);
		
		mt = dbService.getMeasurementType(mtId2);
		assertNull(mt);
	}
	
	private void testAddMeasurementType() {
		
		int nr1 = dbService.countMeasurementTypes();
		
		dbService.addMeasurementType(mt1);
		
		dbService.addMeasurementType(mt2);
		
		MeasurementType mt = dbService.getMeasurementType(mtId1);
		//System.out.println("Inserted 1st measurement type: " + mt);
		
		assertEquals(mtParamSc1, mt.getParameterSc());
		assertEquals(mtParam1, mt.getParameter());
		assertEquals(mtExamTypeSc1, mt.getExaminationTypeSc());
		assertEquals(mtExamType1, mt.getExaminationType());
		assertEquals(mtUnitSc1, mt.getUnitSc());
		assertEquals(mtUnit1, mt.getUnit());
		
		mt = dbService.getMeasurementType(mtId2);
		//System.out.println("Inserted 2nd measurement type: " + mt);
		
		assertEquals(mtParamSc2, mt.getParameterSc());
		assertEquals(mtParam2, mt.getParameter());
		assertEquals(mtExamTypeSc2, mt.getExaminationTypeSc());
		assertEquals(mtExamType2, mt.getExaminationType());
		assertEquals(mtUnitSc2, mt.getUnitSc());
		assertEquals(mtUnit2, mt.getUnit());

		int nr2 = dbService.countMeasurementTypes();
		assertEquals(nr1 + 2, nr2);
	}
	
	private void testUpdateMeasurementType() {

		int nr1 = dbService.countMeasurementTypes();
		
		mt1.setParameter(mtParam1b);
		mt1.setExaminationType(mtExamType1b);
		mt1.setUnit(mtUnit1b);
		dbService.addMeasurementType(mt1);
		
		MeasurementType mt = dbService.getMeasurementType(mtId1);
		//System.out.println("Updated measurement type: " + mt);
		
		assertEquals(mtParam1b, mt.getParameter());
		assertEquals(mtExamType1b, mt.getExaminationType());
		assertEquals(mtUnit1b, mt.getUnit());
		
		int nr2 = dbService.countMeasurementTypes();
		assertEquals(nr1, nr2);
		
	}
	
	private void testDeleteMeasurement() {
		
		Measurement m;
		
		if (date1 != null) {
		
			dbService.deleteMeasurement(stationId, measurementPoint1, mtId1, date1);
			
			m = dbService.getMeasurement(stationId, measurementPoint1, mtId1, date1);
			assertNull(m);
		}
		
		if (date2 != null) {
			dbService.deleteMeasurement(stationId, measurementPoint2, mtId2, date2);
			
			m = dbService.getMeasurement(stationId, measurementPoint2, mtId2, date2);
			assertNull(m);
		}
	}
	
	private void testAddMeasurement() {
		int nr1 = dbService.countMeasurements();
		
		m1 = dbService.saveMeasurement(m1);
		//System.out.println("Inserted 1st measurement: " + m1);
		date1 = m1.getMeasurementDateTime();
		
		Exception ex = assertThrows(UnableToExecuteStatementException.class, () -> {
			dbService.saveMeasurement(m2);
		}); //because the measurement_type does not exist
		assertTrue(ex.getMessage().indexOf("ERROR: insert or update on table \"measurement\" violates foreign key constraint \"fk_1\"") != -1);
		
		m2.setMeasurementTypeId(mtId2);
		m2 = dbService.saveMeasurement(m2);
		//System.out.println("Inserted 2nd measurement: " + m2);
		date2 = m2.getMeasurementDateTime();
		
		int nr2 = dbService.countMeasurements();
		assertEquals(nr1 + 2, nr2);
	}

	private void testUpdateMeasurement() {
		int nr1 = dbService.countMeasurements();
		
		m1.setResult(result1b);
		Measurement m = dbService.saveMeasurement(m1);
		assertNull(m); //the measurement already exists so it is updated
		
		m = dbService.getMeasurement(stationId, measurementPoint1, mtId1, date1);
		//System.out.println("Updated measurement: " + m);
		
		assertEquals(result1b, m.getResult());
		
		int nr2 = dbService.countMeasurements();
		assertEquals(nr1, nr2);
	}
}
