package dk.dataforsyningen.vanda_hydrometry_data.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandLineArgsParserTests {

    CommandLineArgsParser commandLineArgsParser = new CommandLineArgsParser();

    String[] args = {"Cmd1",
            "cmd2",
            "CMD3",
            "cmd4=value",
            "--op1=1,2,3",
            "--op2=abc",
            "--op3"};

    @BeforeEach
    public void setup() {
        commandLineArgsParser.clear();
        commandLineArgsParser.parse(args);
    }

    /***
     * Tests that the arguments are parsed into options (starting with --) and commands.
     */
    @Test
    public void parseTest() {
        assertEquals(4, commandLineArgsParser.getCommands().size());
        assertTrue(commandLineArgsParser.hasCommand("Cmd1"));
        assertTrue(commandLineArgsParser.hasCommand("cmd2"));
        assertTrue(commandLineArgsParser.hasCommand("CMD3"));
        assertFalse(commandLineArgsParser.hasCommand("cmd4"));
        assertTrue(commandLineArgsParser.hasCommand("cmd4=value"));
        assertFalse(commandLineArgsParser.hasCommand("op1"));
        assertFalse(commandLineArgsParser.hasCommand("op2"));
        assertFalse(commandLineArgsParser.hasCommand("op3"));
    }

    /**
     * Tests that parameter and commands names are case insensitive.
     */
    @Test
    public void caseInsensitiveTest() {
        assertTrue(commandLineArgsParser.hasCommand("cmd1"));
        assertTrue(commandLineArgsParser.hasCommand("CMD1"));
        assertTrue(commandLineArgsParser.hasCommand("Cmd2"));
        assertTrue(commandLineArgsParser.hasCommand("CMD2"));
        assertTrue(commandLineArgsParser.hasCommand("cmd3"));
        assertTrue(commandLineArgsParser.hasCommand("Cmd3"));
    }

}
