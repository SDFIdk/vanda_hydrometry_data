package dk.dataforsyningen.vanda_hydrometry_data.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dk.dataforsyningen.vanda_hydrometry_data.command.CommandInterface;
import dk.dataforsyningen.vanda_hydrometry_data.command.StationsCommand;
import dk.dataforsyningen.vanda_hydrometry_data.command.WaterFlowsCommand;
import dk.dataforsyningen.vanda_hydrometry_data.command.WaterLevelsCommand;

@SpringBootTest
public class CommandServiceTest {

	@Autowired
	CommandService commandService;
	
	@Test
	public void testAllCommands() {
		
		Map<String, CommandInterface> cmds = commandService.getAllCommandBeans();
		
		int found = 0;
		for(String cmd : cmds.keySet()) {
			if ("waterlevels".equals(cmd) && cmds.get(cmd) instanceof WaterLevelsCommand) {
				found++;
			}
			if ("waterflows".equals(cmd) && cmds.get(cmd) instanceof WaterFlowsCommand) {
				found++;
			}
			if ("stations".equals(cmd) && cmds.get(cmd) instanceof StationsCommand) {
				found++;
			}
		}
		
		assertEquals(3, found);
	}
	
	@Test
	public void testGetCommand() {
		
		CommandInterface cmd = commandService.getCommandBean("stations");
		
		assertTrue(cmd instanceof StationsCommand);
	}
	
	@Test
	public void testFirstCommand() {
		
		ArrayList<String> cmds = new ArrayList<>();
		cmds.add("command1");
		cmds.add("stations");
		cmds.add("command2");
		
		CommandInterface cmd = commandService.getFirstDefinedCommandBean(cmds);
		
		assertTrue(cmd instanceof StationsCommand);
	}
}
