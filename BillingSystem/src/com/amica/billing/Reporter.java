package com.amica.billing;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;

public class Reporter {
    private final Billing billing;
    private final Path outputFolder;
    private final LocalDate asOf;

    public Reporter(Billing billing, Path outputFolder, LocalDate asOf) {
        billing.addInvoiceListener(this::onInvoiceChanged);
        this.billing = billing;
        this.outputFolder = outputFolder;
        this.asOf = asOf;
    }

    public void reportInvoicesOrderedByNumber() {
        generateReport("invoices_by_number.txt", billing.getInvoicesOrderedByNumber());
    }

    public void reportInvoicesGroupedByCustomer() {
        generateReport("invoices_by_customer.txt", billing.getInvoicesOrderedByNumber());
    }

    public void reportOverdueInvoices() {
        generateReport("overdue_invoices.txt", billing.getInvoicesOrderedByNumber());
    }

    public void reportCustomersAndVolume() {
        generateReport("customers_and_volume.txt", billing.getInvoicesOrderedByNumber());
    }

    private void generateReport(String fileName, Stream<Invoice> invoices) {
        Path reportFile = outputFolder.resolve(fileName);
        try (PrintWriter writer = new PrintWriter(reportFile.toFile())) {
            writer.println("Generated report: " + fileName);
            invoices.forEach(invoice -> {
                writer.println(invoice.getNumber() + ", " + invoice.getCustomer().getName() + ", " + invoice.getAmount());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onInvoiceChanged(Invoice invoice) {
        reportInvoicesOrderedByNumber();
    }
}