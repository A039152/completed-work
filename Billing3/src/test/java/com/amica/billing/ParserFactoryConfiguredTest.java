package com.amica.billing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amica.billing.parse.FlatParser;
import com.amica.billing.parse.Parser;
import com.amica.billing.parse.QuotedCSVParser;
import com.amica.esa.componentconfiguration.manager.ComponentConfigurationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class ParserFactoryConfiguredTest {

    @BeforeAll
    public static void setUpClass() {
        System.setProperty("server.env", "Quoted");
        // If needed, also set "component.name", or rely on single folder
        // System.setProperty("component.name", "MyBilling");
        ComponentConfigurationManager.getInstance().initialize();
    }

    @Test
    public void testDefaultParserIsFlat() {
        Parser parser = ParserFactory.createParser("anyFile.flat");
        // Because we told it to set default to FlatParser
        assertTrue(parser instanceof FlatParser);
    }

    @Test
    public void testCSVParserIsQuoted() {
        Parser parser = ParserFactory.createParser("some.csv");
        assertTrue(parser instanceof QuotedCSVParser);
    }
}
