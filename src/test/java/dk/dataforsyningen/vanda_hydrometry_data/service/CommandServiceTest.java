package dk.dataforsyningen.vanda_hydrometry_data.service;

import dk.dataforsyningen.vanda_hydrometry_data.command.CommandInterface;
import dk.dataforsyningen.vanda_hydrometry_data.command.StationsCommand;
import dk.dataforsyningen.vanda_hydrometry_data.command.StreamDischargeCommand;
import dk.dataforsyningen.vanda_hydrometry_data.command.WaterLevelsCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommandServiceTest {

    @Autowired
    CommandService commandService;

    @Test
    public void testAllCommands() {

        Map<String, CommandInterface> cmds = commandService.getAllCommandBeans();

        int found = 0;
        for (String cmd : cmds.keySet()) {
            if ("waterlevels".equals(cmd) && cmds.get(cmd) instanceof WaterLevelsCommand) {
                found++;
            }
            if ("streamdischarge".equals(cmd) && cmds.get(cmd) instanceof StreamDischargeCommand) {
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

        assertInstanceOf(StationsCommand.class, cmd);
    }

    @Test
    public void testFirstCommand() {

        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("command1");
        cmds.add("stations");
        cmds.add("command2");

        CommandInterface cmd = commandService.getFirstDefinedCommandBean(cmds);

        assertInstanceOf(StationsCommand.class, cmd);
    }
}
