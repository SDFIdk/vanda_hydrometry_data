package dk.dataforsyningen.vanda_hydrometry_data;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import dk.dataforsyningen.vanda_hydrometry_data.command.StationsCommand;
import dk.dataforsyningen.vanda_hydrometry_data.components.CommandController;
import dk.dataforsyningen.vanda_hydrometry_data.service.CommandService;

@SpringBootTest
public class CommandControllerTest {

	
	@Mock
	private CommandService commandService;
	
	
	@Mock
	private VandaHydrometryDataConfig config;
	
	@Mock
	private StationsCommand stationsCommand;
	
	@InjectMocks
	CommandController commandController = new CommandController(); 
	
	@Test
	public void showShortHelpCallTest() {
		
		String command = "stations";
		
		when(commandService.getCommandBean(command)).thenReturn(stationsCommand);
		when(config.isHelp()).thenReturn(true);
		when(config.isSaveDb()).thenReturn(false);
		when(config.isVerbose()).thenReturn(false);
		
		commandController.execute(command);
		
		verify(stationsCommand, never()).getData();
		verify(stationsCommand, never()).mapData();
		verify(stationsCommand, never()).displayData();
		verify(stationsCommand, never()).saveData();
		
		verify(stationsCommand, times(1)).showHelp();	
	}
	
	@Test
	public void getDataCallTest() {
		
		String command = "stations";
		
		when(commandService.getCommandBean(command)).thenReturn(stationsCommand);
		when(config.isHelp()).thenReturn(false);
		when(config.isSaveDb()).thenReturn(false);
		when(config.isVerbose()).thenReturn(false);
		
		commandController.execute(command);
		
		verify(stationsCommand, times(1)).getData();
		verify(stationsCommand, times(1)).mapData();
		verify(stationsCommand, never()).displayData();
		verify(stationsCommand, never()).saveData();
	}
	
	@Test
	public void displayDataCallTest() {
		
		String command = "stations";
		
		when(commandService.getCommandBean(command)).thenReturn(stationsCommand);
		when(config.isHelp()).thenReturn(false);
		when(config.isSaveDb()).thenReturn(false);
		when(config.isVerbose()).thenReturn(true);
		
		commandController.execute(command);
		
		verify(stationsCommand, times(1)).getData();
		verify(stationsCommand, times(1)).mapData();
		verify(stationsCommand, times(1)).displayData();
		verify(stationsCommand, never()).saveData();
	}
	
	@Test
	public void saveDbCallTest() {
		
		String command = "stations";
		
		when(commandService.getCommandBean(command)).thenReturn(stationsCommand);
		when(config.isHelp()).thenReturn(false);
		when(config.isSaveDb()).thenReturn(true);
		when(config.isVerbose()).thenReturn(false);
		
		commandController.execute(command);
		
		verify(stationsCommand, times(1)).getData();
		verify(stationsCommand, times(1)).mapData();
		verify(stationsCommand, never()).displayData();
		verify(stationsCommand, times(1)).saveData();
	}	
}
