package com.amica.billing;

import com.amica.esa.componentconfiguration.manager.ComponentConfigurationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ReporterConfiguredIntegrationTest
        extends ReporterIntegrationTest {

    @BeforeAll
    public static void setUpBeforeAll() {
        System.setProperty("server.env", "Quoted");
        ComponentConfigurationManager manager =
                ComponentConfigurationManager.getInstance();
        manager.initialize();
    }

    @BeforeEach
    @Override
    public void setUp() throws IOException {
        Files.createDirectories(Paths.get(TestUtility.TEMP_FOLDER));
        Files.copy(Paths.get("data/customers_quoted.csv"),
                Paths.get(TestUtility.TEMP_FOLDER, "customers_quoted.csv"),
                StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Paths.get("data/invoices_quoted.csv"),
                Paths.get(TestUtility.TEMP_FOLDER, "invoices_quoted.csv"),
                StandardCopyOption.REPLACE_EXISTING);

        Files.createDirectories(Paths.get(getOutputFolder()));

        reporter = new Reporter();
        billing = reporter.getBilling();
    }

    @Override
    protected String getOutputFolder() {
        return "alternate";
    }
}
