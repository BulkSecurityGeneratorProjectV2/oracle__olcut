package com.oracle.labs.mlrg.olcut.config;


import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TypedProcessorsTest {

	@Test
	public void testStringType() throws Exception {
		ConfigurationManager cm = new ConfigurationManager("/com/oracle/labs/mlrg/olcut/config/typedProcessorsConfig.xml");
		@SuppressWarnings("unchecked")
		TypedProcessorList<String, String> tpl = (TypedProcessorList<String, String>) cm.lookup("typedProcessors");
		List<String> values = tpl.process("Omelet");
		assertEquals(2, values.size());

		assertEquals("McOmeletFace", values.get(0));
		assertEquals("telemO-1170105035", values.get(1));
		cm.close();
	}
}