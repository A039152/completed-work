package com.amica.billing;

import com.amica.esa.componentconfiguration.manager.ComponentConfigurationManager;
import org.junit.jupiter.api.BeforeAll;

public class BillingConfiguredTest extends BillingTest {

    @BeforeAll
    public static void setUpClass() {
        System.setProperty("server.env", "Configured");
       ComponentConfigurationManager manager =
                ComponentConfigurationManager.getInstance();
        manager.initialize();
    }

    @Override
    protected String getCustomersFilename() {
        return "customers_configured.csv";
    }

    @Override
    protected String getInvoicesFilename() {
        return "invoices_configured.csv";
    }

    @Override
    protected Billing createTestTarget() {
       return new Billing();
    }
}
