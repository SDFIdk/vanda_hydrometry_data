package dk.dataforsyningen.vanda_hydrometry_data.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import dk.dataforsyningen.vanda_hydrometry_data.service.VandahDmpApiService;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesLocationResponse;
import dk.miljoeportal.vandah.model.DmpHydroApiResponsesStationResponse;

@SpringBootTest
public class StationsCommandTest {

	DmpHydroApiResponsesStationResponse[] data;
	
	private static UUID uuid1 = UUID.fromString("11111111-2222-3333-4444-aaaaaaaaaaaa");
	private static String id1 = "10000001";
	private static String oldNr1 = "10000002";
	private static String opId1 = "OPSTID-001";
	private static String owner1 = "Owner1 Kph";
	private static String name1 = "Name 001";
	private static String desc1 = "Description 01";
	private static double x1 = 11.23;
	private static double y1 = 12.34;
	
	private static UUID uuid2 = UUID.fromString("11111111-2222-3333-4444-bbbbbbbbbbbb");
	private static String id2 = "20000001";
	private static String oldNr2 = "20000002";
	private static String opId2 = "OPSTID-002";
	private static String owner2 = "Owner2 Kph";
	private static String name2 = "Name 002";
	private static String desc2 = "Description 02";
	private static double x2 = 21.23;
	private static double y2 = 22.34;
	
	@Mock
	private VandahDmpApiService vandahService;
	
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
		station2.setLocation(location);
		
		data[0] = station1;
		data[1] = station2;
		
		when(vandahService.getAllStations()).thenReturn(data);
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
		assertEquals(oldNr1, station1.getOldStationNumber());
		assertEquals(opId1, station1.getOperatorStationId());
		assertEquals(owner1, station1.getStationOwnerName());
		assertEquals(name1, station1.getName());
		assertEquals(desc1, station1.getDescription());
		assertNotNull(station1.getLocation());
		assertEquals(x1, station1.getLocation().getX());
		assertEquals(y1, station1.getLocation().getY());
		
		assertEquals(id2, station2.getStationId());
		assertEquals(oldNr2, station2.getOldStationNumber());
		assertEquals(opId2, station2.getOperatorStationId());
		assertEquals(owner2, station2.getStationOwnerName());
		assertEquals(name2, station2.getName());
		assertEquals(desc2, station2.getDescription());
		assertNotNull(station2.getLocation());
		assertEquals(x2, station2.getLocation().getX());
		assertEquals(y2, station2.getLocation().getY());
	}
}
