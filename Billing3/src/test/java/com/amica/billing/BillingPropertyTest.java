package com.amica.billing;

import com.amica.escm.configuration.api.Configuration;
import com.amica.escm.configuration.properties.PropertiesConfiguration;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
// ... other imports ...

public class BillingPropertyTest extends BillingTest {

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
        Properties props = new Properties();
        props.put(Billing.PROP_CUSTOMERS_FILE,
                TestUtility.TEMP_FOLDER + "/" + getCustomersFilename());
        props.put(Billing.PROP_INVOICES_FILE,
                TestUtility.TEMP_FOLDER + "/" + getInvoicesFilename());

        Configuration config = new PropertiesConfiguration(props);

        return new Billing(config);
    }
}
