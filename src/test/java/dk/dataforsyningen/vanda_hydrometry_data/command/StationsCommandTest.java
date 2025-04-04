package dk.dataforsyningen.vanda_hydrometry_data.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import dk.dataforsyningen.vanda_hydrometry_data.service.DatabaseService;
import dk.dataforsyningen.vanda_hydrometry_data.service.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesLocationResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This tests StationsCommand functions but also the model data objects creation
 */
@SpringBootTest
public class StationsCommandTest {

  private static final UUID uuid1 = UUID.fromString("11111111-2222-3333-4444-aaaaaaaaaaaa");
  private static final String id1 = "10000001";
  private static final String oldNr1 = "10000002";
  private static final String opId1 = "OPSTID-001";
  private static final String owner1 = "Owner1 Kph";
  private static final String name1 = "Name 001";
  private static final String desc1 = "Description 01";
  private static final double x1 = 11.23;
  private static final double y1 = 12.34;
  private static final String srid1 = "25832";
  private static final UUID uuid2 = UUID.fromString("11111111-2222-3333-4444-bbbbbbbbbbbb");
  private static final String id2 = "20000001";
  private static final String oldNr2 = "20000002";
  private static final String opId2 = "OPSTID-002";
  private static final String owner2 = "Owner2 Kph";
  private static final String name2 = "Name 002";
  private static final String desc2 = "Description 02";
  private static final double x2 = 21.23;
  private static final double y2 = 22.34;
  private static final String srid2 = "25832";
  private static final String date1 = "2024-09-25T09:15:00.001Z";
  private static final String date2 = "2024-09-25T08:45:00.001Z";
  private final int mtParamSc = 1233;
  private final Integer mtExamTypeSc = 25;
  DmpHydroApiResponsesStationResponse[] data;
  @Mock
  private VandahDmpApiService vandahService;

  @Mock
  private VandaHydrometryDataConfig config;

  @Mock
  private DatabaseService dbService;

  @InjectMocks
  private StationsCommand cmd = new StationsCommand();

  @BeforeEach
  public void setUp() {
    data = new DmpHydroApiResponsesStationResponse[2];

    DmpHydroApiResponsesStationResponse station1 = new DmpHydroApiResponsesStationResponse();
    station1.setStationUid(uuid1);
    station1.setStationId(id1);
    station1.setOldStationNumber(oldNr1);
    station1.setOperatorStationId(opId1);
    station1.setStationOwnerName(owner1);
    station1.setName(name1);
    station1.setDescription(desc1);
    DmpHydroApiResponsesLocationResponse location = new DmpHydroApiResponsesLocationResponse();
    location.setX(x1);
    location.setY(y1);
    location.setSrid(srid1);
    station1.setLocation(location);

    DmpHydroApiResponsesStationResponse station2 = new DmpHydroApiResponsesStationResponse();
    station2.setStationUid(uuid2);
    station2.setStationId(id2);
    station2.setOldStationNumber(oldNr2);
    station2.setOperatorStationId(opId2);
    station2.setStationOwnerName(owner2);
    station2.setName(name2);
    station2.setDescription(desc2);
    location = new DmpHydroApiResponsesLocationResponse();
    location.setX(x2);
    location.setY(y2);
    location.setSrid(srid2);
    station2.setLocation(location);

    data[0] = station1;
    data[1] = station2;

    when(vandahService.getStations(any(), any(), any(), any(), any(), any(), any())).thenReturn(
        data);

    when(config.getStationId()).thenReturn(id1);
    when(config.getOperatorStationId()).thenReturn(opId1);
    when(config.getParameterSc()).thenReturn(mtParamSc);
    when(config.getWithResultsAfter()).thenReturn(OffsetDateTime.parse(date1));
    when(config.getWithResultsCreatedAfter()).thenReturn(OffsetDateTime.parse(date2));
    when((config.getExaminationTypeSc())).thenReturn(mtExamTypeSc);
  }

  @Test
  public void testGetData() {
    int nr = cmd.getData(); //read mock data

    verify(vandahService, times(1)).getStations(any(), eq(id1), eq(opId1), eq(mtParamSc),
        eq(mtExamTypeSc), eq(OffsetDateTime.parse(date1)), eq(OffsetDateTime.parse(date2)));

    assertEquals(2, nr); //2 station
  }

  @Test
  public void testGetAllData() {

    when(config.getStationId()).thenReturn(null);
    when(config.getOperatorStationId()).thenReturn(null);
    when(config.getParameterSc()).thenReturn(null);
    when(config.getWithResultsAfter()).thenReturn(null);
    when(config.getWithResultsCreatedAfter()).thenReturn(null);
    when((config.getExaminationTypeSc())).thenReturn(null);

    int nr = cmd.getData(); //read mock data

    verify(vandahService, times(1)).getStations(any(), isNull(), isNull(), isNull(), isNull(),
        isNull(), isNull());

    assertEquals(2, nr); //2 station
  }

  @Test
  public void testSaveData() {
    cmd.getData(); //read mock data

    cmd.mapData();

    int nr = cmd.saveData(); //read mock data

    verify(dbService, times(1)).saveStations(cmd.getStations());

    assertEquals(2, nr); //2 station
  }

  @Test
  public void testDataMapping() {
    cmd.getData(); //read mock data

    cmd.mapData();

    ArrayList<Station> stations = cmd.getStations();

    assertNotNull(stations);
    assertEquals(stations.size(), 2);

    Station station1 = stations.get(0);
    Station station2 = stations.get(1);

    assertEquals(uuid1.toString(), station1.getStationUid());
    assertEquals(id1, station1.getStationId());
    assertEquals(oldNr1, station1.getStationIdSav());
    assertEquals(opId1, station1.getOperatorStationId());
    assertEquals(owner1, station1.getStationOwnerName());
    assertEquals(name1, station1.getName());
    assertEquals(desc1, station1.getDescription());
    assertEquals(x1, station1.getGeometryX());
    assertEquals(y1, station1.getGeometryY());

    assertEquals(id2, station2.getStationId());
    assertEquals(oldNr2, station2.getStationIdSav());
    assertEquals(opId2, station2.getOperatorStationId());
    assertEquals(owner2, station2.getStationOwnerName());
    assertEquals(name2, station2.getName());
    assertEquals(desc2, station2.getDescription());
    assertEquals(x2, station2.getGeometryX());
    assertEquals(y2, station2.getGeometryY());
  }
}
