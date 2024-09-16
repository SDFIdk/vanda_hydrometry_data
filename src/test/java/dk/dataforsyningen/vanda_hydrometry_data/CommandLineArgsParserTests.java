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
		assertTrue(commandLineArgsParser.containsParam("op2"));
		assertTrue(commandLineArgsParser.containsParam("op3"));
		assertTrue(commandLineArgsParser.containsParam("op4"));
		assertTrue(commandLineArgsParser.containsParam("op5"));
		assertTrue(commandLineArgsParser.containsParam("op6"));
		assertFalse(commandLineArgsParser.containsParam("op7"));
		assertTrue(commandLineArgsParser.containsParam("op7=v2"));
		assertTrue(commandLineArgsParser.containsParam("op8"));
		assertTrue(commandLineArgsParser.containsParam("op9"));
		assertTrue(commandLineArgsParser.containsParam("op10"));
		assertTrue(commandLineArgsParser.containsParam("op11"));
	}
	
	/**
	 * Tests that parameter and commands names are case insensitive.
	 */
	@Test
	public void caseInsensitiveTest() {
		assertTrue(commandLineArgsParser.containsParam("OP1"));
		assertTrue(commandLineArgsParser.containsParam("op1"));
		assertEquals("v1", commandLineArgsParser.getParameter("OP1"));
		assertEquals("v1", commandLineArgsParser.getParameter("oP1"));
		assertTrue(commandLineArgsParser.containsParam("OP3"));
	}
	
	/**
	 * Tests that values are parsed as expected.
	 */
	@Test
	public void valuesTest() {
		assertEquals("v1", commandLineArgsParser.getParameter("op1"));
		assertNull(commandLineArgsParser.getParameter("op2"));
		assertNull(commandLineArgsParser.getParameter("op3"));
		assertTrue(commandLineArgsParser.getParameter("op4").getClass().isArray());
		assertTrue(commandLineArgsParser.getParameter("op5").getClass().isArray());
		assertTrue(commandLineArgsParser.getParameter("op6").getClass().isArray());
		assertNull(commandLineArgsParser.getParameter("op7"));
		assertNull(commandLineArgsParser.getParameter("op7=v2"));
		assertTrue(commandLineArgsParser.getParameter("op8").getClass().isArray());
		assertTrue(commandLineArgsParser.getParameter("op9").getClass().isArray());
		assertEquals("123", commandLineArgsParser.getParameter("op10"));
		assertEquals("12.3", commandLineArgsParser.getParameter("op11"));
	}
	
	/**
	 * Tests conversion to String, Integer or Double works for the relevant parameters.
	 * If a parameter cannot be converted null is returned. 
	 */
	@Test
	public void conversionTest() {
		assertNull(commandLineArgsParser.getIntegerParameter("op1"));
		assertNull(commandLineArgsParser.getIntegerParameter("op4"));
		assertEquals(123, commandLineArgsParser.getIntegerParameter("op10"));
		assertNull(commandLineArgsParser.getIntegerParameter("op11"));
		
		assertNull(commandLineArgsParser.getDoubleParameter("op1"));
		assertNull(commandLineArgsParser.getDoubleParameter("op6"));
		assertEquals(123.0, commandLineArgsParser.getDoubleParameter("op10"));
		assertEquals(12.3, commandLineArgsParser.getDoubleParameter("op11"));
		
		assertEquals("v1", commandLineArgsParser.getStringParameter("op1"));
		assertEquals("1.2,3.4,5.6", commandLineArgsParser.getStringParameter("op6"));
		assertEquals("123", commandLineArgsParser.getStringParameter("op10"));
		assertEquals("12.3", commandLineArgsParser.getStringParameter("op11"));
	}
	
	/**
	 * Tests that the comma separated values are parsed as arrays and converted to desired type.
	 */
	@Test
	public void arraysTest() {
		assertEquals("1;2;3", a2s(commandLineArgsParser.getParameterAsStringArray("op4")));
		assertEquals("a;b;c", a2s(commandLineArgsParser.getParameterAsStringArray("op5")));
		assertEquals("1.2;3.4;5.6", a2s(commandLineArgsParser.getParameterAsStringArray("op6")));
		assertEquals("1;2.5;3", a2s(commandLineArgsParser.getParameterAsStringArray("op8")));
		assertEquals("1.2;3;5.6", a2s(commandLineArgsParser.getParameterAsStringArray("op9")));
		
		assertEquals("1;2;3", a2s(commandLineArgsParser.getParameterAsIntArray("op4")));
		assertTrue(commandLineArgsParser.getParameterAsIntArray("op5").length == 0);
		assertTrue(commandLineArgsParser.getParameterAsIntArray("op6").length == 0);
		assertEquals("1;3", a2s(commandLineArgsParser.getParameterAsIntArray("op8")));
		assertEquals("3", a2s(commandLineArgsParser.getParameterAsIntArray("op9")));
		
		assertEquals("1.0;2.0;3.0", a2s(commandLineArgsParser.getParameterAsDoubleArray("op4")));
		assertTrue(commandLineArgsParser.getParameterAsDoubleArray("op5").length == 0);
		assertEquals("1.2;3.4;5.6", a2s(commandLineArgsParser.getParameterAsDoubleArray("op6")));
		assertEquals("1.0;2.5;3.0", a2s(commandLineArgsParser.getParameterAsDoubleArray("op8")));
		assertEquals("1.2;3.0;5.6", a2s(commandLineArgsParser.getParameterAsDoubleArray("op9")));
	}
	
	/**
	 * Tests that a single value can be returned as a size 1 array if necessary
	 */
	@Test
	public void single2ArrayTest() {
		assertTrue(commandLineArgsParser.getParameterAsStringArray("op1").getClass().isArray());
		assertTrue(commandLineArgsParser.getParameterAsStringArray("op1").length == 1);
		assertEquals("v1", commandLineArgsParser.getParameterAsStringArray("op1")[0]);
		
		assertTrue(commandLineArgsParser.getParameterAsIntArray("op10").getClass().isArray());
		assertTrue(commandLineArgsParser.getParameterAsIntArray("op10").length == 1);
		assertEquals(123, commandLineArgsParser.getParameterAsIntArray("op10")[0]);
		
		assertTrue(commandLineArgsParser.getParameterAsDoubleArray("op11").getClass().isArray());
		assertTrue(commandLineArgsParser.getParameterAsDoubleArray("op11").length == 1);
		assertEquals(12.3, commandLineArgsParser.getParameterAsDoubleArray("op11")[0]);		
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
