package dk.dataforsyningen.vanda_hydrometry_data;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import dk.dataforsyningen.vanda_hydrometry_data.components.CommandController;
import dk.dataforsyningen.vanda_hydrometry_data.components.CommandLineArgsParser;
import dk.dataforsyningen.vanda_hydrometry_data.components.VandaHUtility;
import dk.dataforsyningen.vanda_hydrometry_data.model.Station;
import dk.dataforsyningen.vanda_hydrometry_data.service.DatabaseService;

@SpringBootTest
public class VandaHydrometryDataRunnerTest {

	
	@SpyBean
	CommandController commandController;
	
	@MockBean
	DatabaseService databaseService;
		
	@Autowired
	CommandLineArgsParser commandLineArgsParser;
	
	@Autowired
	VandaHydrometryDataConfig config;
	
	@Autowired
	@InjectMocks
	private VandaHydrometryDataRunner runner;
		
	@BeforeEach
	public void reset() {
		commandLineArgsParser.clear();
	}
	
	@Test
	public void runTestNoParams() throws Exception {
		
		String[] args = new String[0];
		
		try (MockedStatic<VandaHUtility> mockedStatic = mockStatic(VandaHUtility.class)) {
			runner.run(args);
			mockedStatic.verify(() -> VandaHUtility.logAndPrint(null, null, true, "Commands:\n"), times(1));
		}
	}
	
	@Test
	public void runTestTooManyParams() throws Exception {
		
		String[] args = new String[2];
		args[0] = "command1";
		args[1] = "command2";
		
		try (MockedStatic<VandaHUtility> mockedStatic = mockStatic(VandaHUtility.class)) {
			runner.run(args);
			mockedStatic.verify(() -> VandaHUtility.logAndPrint(any(), eq(Level.WARN), eq(true), eq("Too many commands requested.")), times(1));
		}
	}
	
	@Test
	public void runTestUnknownCommand() throws Exception {
		
		String[] args = new String[1];
		args[0] = "command1";
		
		try (MockedStatic<VandaHUtility> mockedStatic = mockStatic(VandaHUtility.class)) {
			runner.run(args);
			mockedStatic.verify(() -> VandaHUtility.logAndPrint(any(), eq(Level.ERROR), eq(true), eq("No execution bean was regsitered for the given command: command1")), times(1));
		}
	}
	
	@Test
	public void runTestKnownCommand() throws Exception {
		
		String[] args = new String[1];
		args[0] = "stations";
				
		doNothing().when(commandController).execute(any());
		
		runner.run(args);
		verify(commandController, times(1)).execute(args[0]);
	}
	
	@Test
	public void runTestAllStations() throws Exception {
		
		String[] args = new String[1];
		args[0] = "stations";
		
		Station st1 = new Station();
		st1.setStationId("10000001");
		
		Station st2 = new Station();
		st2.setStationId("10000002");
		
		ArrayList<Station> ids = new ArrayList<>();
		ids.add(st1);
		ids.add(st2);
		
		config.setStationId("all");
				
		when(databaseService.getAllStations()).thenReturn(ids);
		doNothing().when(commandController).execute(any());
		
		runner.run(args);
		verify(commandController, times(2)).execute(args[0]);
	}
	
	@Test
	public void runTestMultipleStations() throws Exception {
		
		String[] args = new String[1];
		args[0] = "stations";
		
		Station st1 = new Station();
		st1.setStationId("10000001");
		
		Station st2 = new Station();
		st2.setStationId("10000002");
		
		ArrayList<Station> ids = new ArrayList<>();
		ids.add(st1);
		ids.add(st2);
		
		config.setStationId("10000001,10000002");
		doNothing().when(commandController).execute(any());
		
		runner.run(args);
		verify(commandController, times(2)).execute(args[0]);
	}
	
	
}
