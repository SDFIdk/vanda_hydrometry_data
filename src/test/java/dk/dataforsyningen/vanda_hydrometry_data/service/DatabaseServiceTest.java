package dk.dataforsyningen.vanda_hydrometry_data.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.model.Measurement;
import dk.dataforsyningen.vanda_hydrometry_data.model.MeasurementType;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import java.time.OffsetDateTime;
import java.util.List;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test suite to test the DAO and mapping classes and database service.
 * The tests need to be activated from the properties file by:
 * vanda-hydrometry-data.database.test=true
 */
@SpringBootTest
public class DatabaseServiceTest {

  private final String stationId = "S1234567"; //should be fake
  private final String stationName = "name";
  private final String stationOwner = "owner";
  private final String stationDescription = "description";
  private final String stationName2 = "Kbh water station";
  private final String stationOwner2 = "Kbh mun.";
  private final String stationDescription2 = "water station in Kbh";
  private final String stationIdSav = "12345678";
  private final double geometryX = 12.34;
  private final double geometryY = 56.78;
  private final int geometrySrid = 25832;

  private final int mtParamSc1 = 1233;
  private final String mtParam1 = "WaterLevel";
  private final String mtParam1b = "Vandstand";
  private final int mtExamTypeSc1 = 999925; //should be fake
  private final String mtExamType1 = "WaterLevel";
  private final String mtExamType1b = "Vandstand";
  private final int mtUnitSc1 = 19;
  private final String mtUnit1 = "CM";
  private final String mtUnit1b = "cm";

  private final int mtParamSc2 = 1155;
  private final String mtParam2 = "Vandføring";
  private final int mtExamTypeSc2 = 999927; //should be fake
  private final String mtExamType2 = "Vandføring";
  private final int mtUnitSc2 = 55;
  private final String mtUnit2 = "l/s";

  private final int measurementPoint1 = 1;
  private final int measurementPoint2 = 2;
  private final double value1 = 12.34;
  private final double value1b = 11.99;
  private final double value2 = 56.78;
  private final double valueElevationCorrected1 = 123.0;
  private final double valueElevationCorrected1b = 122.9;
  private final double valueElevationCorrected2 = 230.5;

  Station station1;
  Station station2;
  MeasurementType mt1;
  MeasurementType mt2;
  Measurement m1;
  Measurement m2;
  OffsetDateTime date1;
  OffsetDateTime date2;
  @Autowired
  VandaHydrometryDataConfig config;
  private boolean enableTest = false;
  @Autowired
  private DatabaseService dbService;

  @BeforeEach
  public void setup() {

    enableTest = config.isEnableTest();

    System.out.println("Database testing " + (enableTest ? "enabled" : "disabled"));
      if (!enableTest) {
          return;
      }

    mt1 = new MeasurementType();
    mt1.setParameterSc(mtParamSc1);
    mt1.setParameter(mtParam1);
    mt1.setExaminationTypeSc(mtExamTypeSc1);
    mt1.setExaminationType(mtExamType1);
    mt1.setUnitSc(mtUnitSc1);
    mt1.setUnit(mtUnit1);

    mt2 = new MeasurementType();
    mt2.setParameterSc(mtParamSc2);
    mt2.setParameter(mtParam2);
    mt2.setExaminationTypeSc(mtExamTypeSc2);
    mt2.setExaminationType(mtExamType2);
    mt2.setUnitSc(mtUnitSc2);
    mt2.setUnit(mtUnit2);

    station1 = new Station();
    station1.setStationId(stationId);
    station1.setName(stationName);
    station1.setStationOwnerName(stationOwner);
    station1.setDescription(stationDescription);
    station1.setStationIdSav(stationIdSav);
    station1.setGeometryX(geometryX);
    station1.setGeometryY(geometryY);
    station1.setGeometrySrid(geometrySrid);
    station1.getMeasurementTypes().add(mt1);
    station1.getMeasurementTypes().add(mt2);

    station2 = new Station();
    station2.setStationId(stationId);
    station2.setName(stationName);
    station2.setStationOwnerName(stationOwner);
    station2.setDescription(stationDescription);
    station2.setStationIdSav(stationIdSav);
    station2.setGeometryX(geometryX);
    station2.setGeometryY(geometryY);
    station2.setGeometrySrid(geometrySrid);
    station2.getMeasurementTypes().add(mt1);

    m1 = new Measurement();
    m1.setStationId(stationId);
    m1.setIsCurrent(true);
    m1.setMeasurementPointNumber(measurementPoint1);
    m1.setValue(value1);
    m1.setValueElevationCorrected(valueElevationCorrected1);
    m1.setExaminationTypeSc(mtExamTypeSc1);
    m1.setMeasurementDateTime(OffsetDateTime.now());

    m2 = new Measurement();
    m2.setStationId(stationId);
    m2.setIsCurrent(true);
    m2.setMeasurementPointNumber(measurementPoint2);
    m2.setValue(value2);
    m2.setValueElevationCorrected(valueElevationCorrected2);
    m2.setExaminationTypeSc(0);
    m2.setMeasurementDateTime(OffsetDateTime.now());
  }

  /**
   * Clean the inserted items
   */
  @AfterEach
  public void deleteAll() {

      if (!enableTest) {
          return;
      }

    dbService.deleteStationMeasurementTypeRelation(station1.getStationId());

    testDeleteMeasurement();

    testDeleteMeasurementByStation();

    testDeleteMeasurementType();

    testDeleteStation();
  }

  @Test
  public void testDAO() throws InterruptedException {

      if (!enableTest) {
          return;
      }

    //Test StationDao

    //can only delete station if relation station/measurement type is not present
    dbService.deleteStationMeasurementTypeRelation(station1.getStationId());

    testDeleteStation();

    testAddStations();

    Thread.sleep(1000); //so the update time is later

    testUpdateStations();

    // MeasurementTypeDao

    //can only delete measurement_type if relation station/measurement type is not present
    dbService.deleteStationMeasurementTypeRelation(station1.getStationId());
    testDeleteMeasurementByStation();

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
    assertEquals(stationIdSav, station.getStationIdSav());
    assertEquals(stationDescription, station.getDescription());
    assertEquals(geometryX, station.getGeometryX());
    assertEquals(geometryY, station.getGeometryY());
    assertEquals(geometrySrid, station.getGeometrySrid());
    assertEquals(2, station.getMeasurementTypes().size());
    assertTrue(mt1.equals(station.getMeasurementTypes().get(0)) ||
        mt1.equals(station.getMeasurementTypes().get(1)));
    assertTrue(mt2.equals(station.getMeasurementTypes().get(0)) ||
        mt2.equals(station.getMeasurementTypes().get(1)));

    assertNotNull(station.getCreated());
    assertNotNull(station.getUpdated());
    assertEquals(station.getCreated(), station.getUpdated());
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
    dbService.deleteMeasurementType(mtExamTypeSc1);

    MeasurementType mt = dbService.getMeasurementType(mtExamTypeSc1);
    assertNull(mt);

    dbService.deleteMeasurementType(mtExamTypeSc2);

    mt = dbService.getMeasurementType(mtExamTypeSc2);
    assertNull(mt);
  }

  private void testAddMeasurementType() {

    int nr1 = dbService.countMeasurementTypes();

    dbService.addMeasurementType(mt1);

    dbService.addMeasurementType(mt2);

    MeasurementType mt = dbService.getMeasurementType(mtExamTypeSc1);
    //System.out.println("Inserted 1st measurement type: " + mt);

    assertEquals(mtParamSc1, mt.getParameterSc());
    assertEquals(mtParam1, mt.getParameter());
    assertEquals(mtExamTypeSc1, mt.getExaminationTypeSc());
    assertEquals(mtExamType1, mt.getExaminationType());
    assertEquals(mtUnitSc1, mt.getUnitSc());
    assertEquals(mtUnit1, mt.getUnit());

    mt = dbService.getMeasurementType(mtExamTypeSc2);
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

    MeasurementType mt = dbService.getMeasurementType(mtExamTypeSc1);
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

      dbService.deleteMeasurement(stationId, measurementPoint1, mtExamTypeSc1, date1);

      m = dbService.getMeasurement(stationId, measurementPoint1, mtExamTypeSc1, date1);
      assertNull(m);
    }

    if (date2 != null) {
      dbService.deleteMeasurement(stationId, measurementPoint2, mtExamTypeSc2, date2);

      m = dbService.getMeasurement(stationId, measurementPoint2, mtExamTypeSc2, date2);
      assertNull(m);
    }
  }

  private void testDeleteMeasurementByStation() {
    dbService.deleteMeasurementForStation(stationId);
  }

  private void testAddMeasurement() {
    int nr1 = dbService.countAllMeasurements();

    m1 = dbService.saveMeasurement(m1);
    //System.out.println("Inserted 1st measurement: " + m1);
    date1 = m1.getMeasurementDateTime();

    List<Measurement> mHistory = dbService.getMeasurementHistory(stationId, measurementPoint1,
        mtExamTypeSc1, date1);
    assertEquals(1, mHistory.size());
    assertEquals(m1.getStationId(), mHistory.getFirst().getStationId());
    assertEquals(m1.getMeasurementDateTime(), mHistory.getFirst().getMeasurementDateTime());
    assertEquals(m1.getValue(), mHistory.getFirst().getValue());
    assertEquals(m1.getValueElevationCorrected(),
        mHistory.getFirst().getValueElevationCorrected());

    Measurement mDb = dbService.getMeasurement(m1.getStationId(), m1.getMeasurementPointNumber(),
        m1.getExaminationTypeSc(), date1);
    assertEquals(mHistory.getFirst().getStationId(), mDb.getStationId());
    assertEquals(mHistory.getFirst().getMeasurementDateTime(), mDb.getMeasurementDateTime());
    assertEquals(mHistory.getFirst().getValue(), mDb.getValue());
    assertEquals(mHistory.getFirst().getValueElevationCorrected(),
        mDb.getValueElevationCorrected());

    Exception ex = assertThrows(UnableToExecuteStatementException.class, () -> {
      dbService.saveMeasurement(m2);
    }); //because the measurement_type does not exist
    assertTrue(ex.getMessage().indexOf(
        "ERROR: insert or update on table \"measurement\" violates foreign key constraint \"fk_1\"") !=
        -1);

    m2.setExaminationTypeSc(mtExamTypeSc2);
    m2 = dbService.saveMeasurement(m2);
    //System.out.println("Inserted 2nd measurement: " + m2);
    date2 = m2.getMeasurementDateTime();

    mHistory = dbService.getMeasurementHistory(stationId, measurementPoint2,
        mtExamTypeSc2, date2);
    assertEquals(1, mHistory.size());
    assertEquals(m2.getStationId(), mHistory.getFirst().getStationId());
    assertEquals(m2.getMeasurementDateTime(), mHistory.getFirst().getMeasurementDateTime());
    assertEquals(m2.getValue(), mHistory.getFirst().getValue());
    assertEquals(m2.getValueElevationCorrected(),
        mHistory.getFirst().getValueElevationCorrected());

    mDb = dbService.getMeasurement(m2.getStationId(), m2.getMeasurementPointNumber(),
        m2.getExaminationTypeSc(), date2);
    assertEquals(mHistory.getFirst().getStationId(), mDb.getStationId());
    assertEquals(mHistory.getFirst().getMeasurementDateTime(), mDb.getMeasurementDateTime());
    assertEquals(mHistory.getFirst().getValue(), mDb.getValue());
    assertEquals(mHistory.getFirst().getValueElevationCorrected(),
        mDb.getValueElevationCorrected());

    int nr2 = dbService.countAllMeasurements();
    assertEquals(nr1 + 2, nr2);
  }

  private void testUpdateMeasurement() {
    int nr1 = dbService.countAllMeasurements();

    m1.setValue(value1b);
    m1.setValueElevationCorrected(valueElevationCorrected1b);
    dbService.saveMeasurement(m1);

    List<Measurement> mHistory = dbService.getMeasurementHistory(stationId, measurementPoint1,
        mtExamTypeSc1, date1);
    assertEquals(2, mHistory.size());
    assertNotNull(mHistory.getFirst().getCreated());
    assertNotNull(mHistory.getLast().getCreated());
    assertTrue(!mHistory.getFirst().getIsCurrent() &&
        mHistory.getLast().getIsCurrent()); //one record is active the other one not
    assertTrue(mHistory.getFirst().getCreated().isBefore(mHistory.getLast().getCreated()));
    assertTrue(mHistory.getFirst().getValue().doubleValue() !=
        mHistory.getLast().getValue().doubleValue());
    assertTrue(mHistory.getFirst().getValueElevationCorrected().doubleValue() !=
        mHistory.getLast().getValueElevationCorrected().doubleValue());

    Measurement m = dbService.getMeasurement(stationId, measurementPoint1, mtExamTypeSc1, date1);
    //System.out.println("Updated measurement: " + m);

    assertEquals(value1b, m.getValue());
    assertEquals(valueElevationCorrected1b, m.getValueElevationCorrected());

    int nr2 = dbService.countAllMeasurements();
    assertEquals(nr1 + 1, nr2);
  }

  @Test
  public void testStationMeasurementTypeAddition() {

      if (!enableTest) {
          return;
      }

    //can only delete station if relation station/measurement type is not present
    dbService.deleteStationMeasurementTypeRelation(station2.getStationId());

    testDeleteStation();

    dbService.saveStation(station2);

    Station station = dbService.getStation(stationId);

    assertEquals(stationId, station.getStationId());
    assertEquals(stationName, station.getName());
    assertEquals(stationOwner, station.getStationOwnerName());
    assertEquals(stationIdSav, station.getStationIdSav());
    assertEquals(stationDescription, station.getDescription());
    assertEquals(geometryX, station.getGeometryX());
    assertEquals(geometryY, station.getGeometryY());
    assertEquals(geometrySrid, station.getGeometrySrid());
    assertEquals(1, station.getMeasurementTypes().size());
    assertEquals(mt1, station.getMeasurementTypes().getFirst());

    station2.getMeasurementTypes().add(mt2);

    dbService.saveStation(station2);

    station = dbService.getStation(stationId);

    assertEquals(2, station.getMeasurementTypes().size());
    assertTrue(mt1.equals(station.getMeasurementTypes().get(0)) ||
        mt1.equals(station.getMeasurementTypes().get(1)));
    assertTrue(mt2.equals(station.getMeasurementTypes().get(0)) ||
        mt2.equals(station.getMeasurementTypes().get(1)));

  }
}


