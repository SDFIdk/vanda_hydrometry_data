package dk.dataforsyningen.vanda_hydrometry_data.components;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dk.dataforsyningen.vanda_hydrometry_data.VandaHydrometryDataConfig;
import dk.dataforsyningen.vanda_hydrometry_data.command.StationsCommand;
import dk.dataforsyningen.vanda_hydrometry_data.service.CommandService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommandControllerTest {


  @InjectMocks
  CommandController commandController = new CommandController();
  @Mock
  private CommandService commandService;
  @Mock
  private VandaHydrometryDataConfig config;
  @Mock
  private StationsCommand stationsCommand;

  @Test
  public void showShortHelpCallTest() {

    when(config.isHelp()).thenReturn(true);
    when(config.isSaveDb()).thenReturn(false);
    when(config.isDisplayData()).thenReturn(false);

    commandController.execute(stationsCommand);

    verify(stationsCommand, never()).getData();
    verify(stationsCommand, never()).mapData();
    verify(stationsCommand, never()).displayData(false);
    verify(stationsCommand, never()).saveData();

    verify(stationsCommand, times(1)).showHelp();
  }

  @Test
  public void getDataCallTest() {

    when(config.isHelp()).thenReturn(false);
    when(config.isSaveDb()).thenReturn(false);
    when(config.isDisplayData()).thenReturn(false);

    commandController.execute(stationsCommand);

    verify(stationsCommand, times(1)).getData();
    verify(stationsCommand, times(1)).mapData();
    verify(stationsCommand, never()).displayData(false);
    verify(stationsCommand, never()).saveData();
  }

  @Test
  public void displayDataCallTest() {

    when(config.isHelp()).thenReturn(false);
    when(config.isSaveDb()).thenReturn(false);
    when(config.isDisplayData()).thenReturn(true);

    commandController.execute(stationsCommand);

    verify(stationsCommand, times(1)).getData();
    verify(stationsCommand, times(1)).mapData();
    verify(stationsCommand, times(1)).displayData(false);
    verify(stationsCommand, never()).saveData();
  }

  @Test
  public void displayRawDataCallTest() {

    when(config.isHelp()).thenReturn(false);
    when(config.isSaveDb()).thenReturn(false);
    when(config.isDisplayRawData()).thenReturn(true);
    when(config.isDisplayData()).thenReturn(false);

    commandController.execute(stationsCommand);

    verify(stationsCommand, times(1)).getData();
    verify(stationsCommand, times(1)).mapData();
    verify(stationsCommand, times(1)).displayData(true);
    verify(stationsCommand, never()).saveData();
    verify(stationsCommand, never()).displayData(false);
  }

  @Test
  public void saveDbCallTest() {

    when(config.isHelp()).thenReturn(false);
    when(config.isSaveDb()).thenReturn(true);
    when(config.isDisplayData()).thenReturn(false);

    commandController.execute(stationsCommand);

    verify(stationsCommand, times(1)).getData();
    verify(stationsCommand, times(1)).mapData();
    verify(stationsCommand, never()).displayData(false);
    verify(stationsCommand, times(1)).saveData();
  }
}
