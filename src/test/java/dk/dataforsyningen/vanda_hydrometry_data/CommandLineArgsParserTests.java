package dk.dataforsyningen.vanda_hydrometry_data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandLineArgsParserTests {

	CommandLineArgsParser commandLineArgsParser = new CommandLineArgsParser();
	
	String[] args = {"--Op1=v1", 
	                 "--op2",
	                 "op3", 
	                 "--op4=1,2,3", 
	                 "--op5=a,b,c", 
	                 "--op6=1.2,3.4,5.6",
	                 "op7=v2",
	                 "--op8=1,2.5,3",
	                 "--op9=1.2,3,5.6",
	                 "--op10=123",
	                 "--op11=12.3"};
	
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
		assertEquals(9, commandLineArgsParser.getOptions().size());
		assertEquals(2, commandLineArgsParser.getCommands().size());
		assertTrue(commandLineArgsParser.containsParam("op1"));
		assertTrue(commandLineArgsParser.hasOption("op1"));
		assertTrue(commandLineArgsParser.containsParam("op2"));
		assertTrue(commandLineArgsParser.hasOption("op2"));
		assertTrue(commandLineArgsParser.containsParam("op3"));
		assertTrue(commandLineArgsParser.hasCommand("op3"));
		assertFalse(commandLineArgsParser.hasOption("op3"));
		assertTrue(commandLineArgsParser.containsParam("op4"));
		assertTrue(commandLineArgsParser.hasOption("op4"));
		assertTrue(commandLineArgsParser.containsParam("op5"));
		assertTrue(commandLineArgsParser.hasOption("op5"));
		assertTrue(commandLineArgsParser.containsParam("op6"));
		assertTrue(commandLineArgsParser.hasOption("op6"));
		assertFalse(commandLineArgsParser.containsParam("op7"));
		assertFalse(commandLineArgsParser.hasOption("op7=v2"));
		assertTrue(commandLineArgsParser.containsParam("op7=v2"));
		assertTrue(commandLineArgsParser.hasCommand("op7=v2"));
		assertTrue(commandLineArgsParser.containsParam("op8"));
		assertTrue(commandLineArgsParser.hasOption("op8"));
		assertTrue(commandLineArgsParser.containsParam("op9"));
		assertTrue(commandLineArgsParser.hasOption("op9"));
		assertTrue(commandLineArgsParser.containsParam("op10"));
		assertTrue(commandLineArgsParser.hasOption("op10"));
		assertTrue(commandLineArgsParser.containsParam("op11"));
		assertTrue(commandLineArgsParser.hasOption("op11"));
	}
	
	/**
	 * Tests that parameter and commands names are case insensitive.
	 */
	@Test
	public void caseInsensitiveTest() {
		assertTrue(commandLineArgsParser.containsParam("OP1"));
		assertTrue(commandLineArgsParser.containsParam("op1"));
		assertEquals("v1", commandLineArgsParser.getOption("OP1"));
		assertEquals("v1", commandLineArgsParser.getOption("oP1"));
		assertTrue(commandLineArgsParser.containsParam("OP3"));
	}
	
	/**
	 * Tests that values are parsed as expected.
	 */
	@Test
	public void valuesTest() {
		assertEquals("v1", commandLineArgsParser.getOption("op1"));
		assertNull(commandLineArgsParser.getOption("op2"));
		assertNull(commandLineArgsParser.getOption("op3"));
		assertTrue(commandLineArgsParser.getOption("op4").getClass().isArray());
		assertTrue(commandLineArgsParser.getOption("op5").getClass().isArray());
		assertTrue(commandLineArgsParser.getOption("op6").getClass().isArray());
		assertNull(commandLineArgsParser.getOption("op7"));
		assertNull(commandLineArgsParser.getOption("op7=v2"));
		assertTrue(commandLineArgsParser.getOption("op8").getClass().isArray());
		assertTrue(commandLineArgsParser.getOption("op9").getClass().isArray());
		assertEquals("123", commandLineArgsParser.getOption("op10"));
		assertEquals("12.3", commandLineArgsParser.getOption("op11"));
	}
	
	/**
	 * Tests conversion to String, Integer or Double works for the relevant parameters.
	 * If a parameter cannot be converted null is returned. 
	 */
	@Test
	public void conversionTest() {
		assertNull(commandLineArgsParser.getIntegerOption("op1"));
		assertNull(commandLineArgsParser.getIntegerOption("op4"));
		assertEquals(123, commandLineArgsParser.getIntegerOption("op10"));
		assertNull(commandLineArgsParser.getIntegerOption("op11"));
		
		assertNull(commandLineArgsParser.getDoubleOption("op1"));
		assertNull(commandLineArgsParser.getDoubleOption("op6"));
		assertEquals(123.0, commandLineArgsParser.getDoubleOption("op10"));
		assertEquals(12.3, commandLineArgsParser.getDoubleOption("op11"));
		
		assertEquals("v1", commandLineArgsParser.getStringOption("op1"));
		assertEquals("1.2,3.4,5.6", commandLineArgsParser.getStringOption("op6"));
		assertEquals("123", commandLineArgsParser.getStringOption("op10"));
		assertEquals("12.3", commandLineArgsParser.getStringOption("op11"));
	}
	
	/**
	 * Tests that the comma separated values are parsed as arrays and converted to desired type.
	 */
	@Test
	public void arraysTest() {
		assertEquals("1;2;3", a2s(commandLineArgsParser.getOptionAsStringArray("op4")));
		assertEquals("a;b;c", a2s(commandLineArgsParser.getOptionAsStringArray("op5")));
		assertEquals("1.2;3.4;5.6", a2s(commandLineArgsParser.getOptionAsStringArray("op6")));
		assertEquals("1;2.5;3", a2s(commandLineArgsParser.getOptionAsStringArray("op8")));
		assertEquals("1.2;3;5.6", a2s(commandLineArgsParser.getOptionAsStringArray("op9")));
		
		assertEquals("1;2;3", a2s(commandLineArgsParser.getOptionAsIntArray("op4")));
		assertTrue(commandLineArgsParser.getOptionAsIntArray("op5").length == 0);
		assertTrue(commandLineArgsParser.getOptionAsIntArray("op6").length == 0);
		assertEquals("1;3", a2s(commandLineArgsParser.getOptionAsIntArray("op8")));
		assertEquals("3", a2s(commandLineArgsParser.getOptionAsIntArray("op9")));
		
		assertEquals("1.0;2.0;3.0", a2s(commandLineArgsParser.getOptionAsDoubleArray("op4")));
		assertTrue(commandLineArgsParser.getOptionAsDoubleArray("op5").length == 0);
		assertEquals("1.2;3.4;5.6", a2s(commandLineArgsParser.getOptionAsDoubleArray("op6")));
		assertEquals("1.0;2.5;3.0", a2s(commandLineArgsParser.getOptionAsDoubleArray("op8")));
		assertEquals("1.2;3.0;5.6", a2s(commandLineArgsParser.getOptionAsDoubleArray("op9")));
	}
	
	/**
	 * Tests that a single value can be returned as a size 1 array if necessary
	 */
	@Test
	public void single2ArrayTest() {
		assertTrue(commandLineArgsParser.getOptionAsStringArray("op1").getClass().isArray());
		assertTrue(commandLineArgsParser.getOptionAsStringArray("op1").length == 1);
		assertEquals("v1", commandLineArgsParser.getOptionAsStringArray("op1")[0]);
		
		assertTrue(commandLineArgsParser.getOptionAsIntArray("op10").getClass().isArray());
		assertTrue(commandLineArgsParser.getOptionAsIntArray("op10").length == 1);
		assertEquals(123, commandLineArgsParser.getOptionAsIntArray("op10")[0]);
		
		assertTrue(commandLineArgsParser.getOptionAsDoubleArray("op11").getClass().isArray());
		assertTrue(commandLineArgsParser.getOptionAsDoubleArray("op11").length == 1);
		assertEquals(12.3, commandLineArgsParser.getOptionAsDoubleArray("op11")[0]);		
	}
	
	private String a2s(Object a) {
		if (a == null) return null;
		if (!a.getClass().isArray()) return a.toString();
		
		String s  ="";
		String d = "";
		for(Object v: (Object[])a) {
			s += d + v.toString();
			d = ";";
		}
		return s;
	}
	
}
