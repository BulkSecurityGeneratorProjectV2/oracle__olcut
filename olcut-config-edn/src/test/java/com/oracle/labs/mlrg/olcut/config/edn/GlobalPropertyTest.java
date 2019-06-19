/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.labs.mlrg.olcut.config.edn;

import com.oracle.labs.mlrg.olcut.config.ConfigLoaderException;
import com.oracle.labs.mlrg.olcut.config.ConfigurationManager;
import com.oracle.labs.mlrg.olcut.config.ConfigurationManagerUtils;
import com.oracle.labs.mlrg.olcut.config.PropertyException;
import com.oracle.labs.mlrg.olcut.config.StringConfigurable;
import com.oracle.labs.mlrg.olcut.config.StringListConfigurable;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 *
 */
public class GlobalPropertyTest {

    public GlobalPropertyTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
        ConfigurationManager.addFileFormatFactory(new EdnConfigFactory());
    }


    @Test
    public void noProperty() throws IOException, PropertyException {
        assertThrows(PropertyException.class, () -> {
            ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
            StringConfigurable sc = (StringConfigurable) cm.lookup("unknown");
        });
    }

    @Test
    public void badlyFormed() throws IOException, PropertyException {
        assertThrows(PropertyException.class, () -> {
            ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
            StringConfigurable sc = (StringConfigurable) cm.lookup("badlyformed");

        });
    }

    @Test
    public void invalidGlobalProperty() {
        assertThrows(ConfigLoaderException.class, () -> {
            ConfigurationManager cm = new ConfigurationManager("invalidGlobalPropertyConfig.edn");
        });
    }

    @Test
    public void simpleReplacement() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringConfigurable sc = (StringConfigurable) cm.lookup("simple");
        assertEquals(sc.one, "alpha");
        assertEquals(sc.two, "beta");
        assertEquals(sc.three, "charlie");
    }

    @Test
    public void compoundReplacement() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringConfigurable sc = (StringConfigurable) cm.lookup("compound");
        assertEquals(sc.one, "alpha/beta");
        assertEquals(sc.two, "betacharlie");
        assertEquals(sc.three, "charlie:alpha");
    }
    
    @Test
    public void nonGlobals() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringConfigurable sc = (StringConfigurable) cm.lookup("nonglobal");
        assertEquals(sc.one, "${a");
        assertEquals(sc.two, "$b}");
        assertEquals(sc.three, "$c");
    }

    @Test
    public void recurse() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringConfigurable sc = (StringConfigurable) cm.lookup("recurse");
        assertEquals(sc.one, "alpha");
        assertEquals(sc.two, "alpha");
        assertEquals(sc.three, "alpha");
    }
    
    @Test
    public void recurse2() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringConfigurable sc = (StringConfigurable) cm.lookup("recurse2");
        assertEquals("alpha/bar", sc.one);
        assertEquals(sc.two, "x");
        assertEquals(sc.three, "y");
    }
    
    @Test
    public void recurse3() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringConfigurable sc = (StringConfigurable) cm.lookup("recurse3");
        assertEquals("/tmp/alpha", sc.one);
        assertEquals(sc.two, "/tmp/alpha/bpath");
        assertEquals(sc.three, "y");
        assertEquals("/tmp/alpha", cm.getGlobalProperty("apath"));
    }
    
    @Test
    public void compoundRecurse() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringConfigurable sc = (StringConfigurable) cm.lookup("compoundrecurse");
        assertEquals(sc.one, "one beta/alpha");
        assertEquals(sc.two, "two charlie/alpha/beta/alpha");
        assertEquals(sc.three, "three alpha/beta/charlie");
    }
    
    @Test
    public void distinguishedProps() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringConfigurable sc = (StringConfigurable) cm.lookup("distinguished");
        assertEquals(ConfigurationManagerUtils.getHostName(), sc.one);
        assertEquals(System.getProperty("user.name"), sc.two);
    }
    
    @Test
    public void stringList() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("globalPropertyConfig.edn");
        StringListConfigurable slc = (StringListConfigurable) cm.lookup("listTest");
        assertEquals("alpha", slc.strings.get(0));
        assertEquals("beta", slc.strings.get(1));
        assertEquals("alpha/beta", slc.strings.get(2));
        assertEquals("intro/beta", slc.strings.get(3));
        assertEquals("alpha/extro", slc.strings.get(4));
    }
}