package com.oracle.labs.mlrg.olcut.config.json;

import com.oracle.labs.mlrg.olcut.config.ConfigurationManager;
import com.oracle.labs.mlrg.olcut.config.PathConfigurable;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 */
public class PathTest {

    @BeforeAll
    public static void setUpClass() throws Exception {
        ConfigurationManager.addFileFormatFactory(new JsonConfigFactory());
    }

    @Test
    public void test() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("pathConfig.json");
        PathConfigurable pc = (PathConfigurable) cm.lookup(
                "pathTest");
        String actualPath = pc.getPath().toString();
        actualPath = actualPath.replace('\\', '/');
        
        assertEquals("/this/is/a/test/path", actualPath);
    }

}
