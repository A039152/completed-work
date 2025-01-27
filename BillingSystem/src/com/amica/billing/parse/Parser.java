package com.amica.billing.parse;

import com.amica.billing.Customer;
import com.amica.billing.Invoice;

import java.util.Map;
import java.util.stream.Stream;

public interface Parser {
    /**
     * Returns a stream of {@link Customer}s, one for each text representation.
     */
    public Stream<Customer> parseCustomers(Stream<String> customerLines);

    /**
     * Returns a stream of {@link Invoice}s, one for each text representation.
     * The provided map of customer names to customer records is used to
     * translate the customer names found in the text representation to
     * references to {@link Customer} objects, which we're assuming
     * are loaded first.
     */
    public Stream<Invoice> parseInvoices(Stream<String> invoiceLines,
                                         Map<String, Customer> customers);

    /**
     * Writes the given stream of customers to the given writer.
     */
    public Stream<String> produceCustomers(Stream<Customer> customers);

    /**
     * Writes the given stream of invoices to the given writer.
     */
    public Stream<String> produceInvoices(Stream<Invoice> invoices);
}
