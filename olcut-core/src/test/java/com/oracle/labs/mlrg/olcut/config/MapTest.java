package com.oracle.labs.mlrg.olcut.config;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the extraction of {@link java.util.Map} objects from a {@link PropertySheet}.
 */

public class MapTest {
    public MapTest() { }

    @Test
    public void mapTest() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("mapConfig.xml");
        MapConfigurable m = (MapConfigurable) cm.lookup("mapTest");
        Map<String,String> map = m.map;
        Assert.assertEquals("stuff",map.get("things"));
        Assert.assertEquals("quux",map.get("foo"));
        Assert.assertNull(map.get("bar"));
    }
}