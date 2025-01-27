package com.amica.billing;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.amica.billing.parse.Parser;
import com.amica.billing.parse.ParserFactory;

public class Billing{
    private final String customerFile;
    private final String invoiceFile;
    private final Map<String, Customer> customers;
    private final List<Invoice> invoices;
    private final Reporter reporter;
    private final Parser parser;
    private final List<Consumer<Invoice>> invoiceListeners;

    public Billing(String customerFile, String invoiceFile, String outputFolder, LocalDate reportDate) throws IOException {
        this.customerFile = customerFile;
        this.invoiceFile = invoiceFile;
        this.parser = ParserFactory.createParser(customerFile);
        this.customers = parser.parseCustomers(Files.lines(Paths.get(customerFile)))
                .collect(Collectors.toMap(Customer::getName, customer -> customer));
        this.invoices = parser.parseInvoices(Files.lines(Paths.get(invoiceFile)), customers)
                .collect(Collectors.toList());
        this.invoiceListeners = new ArrayList<>();
        this.reporter = new Reporter(this, Paths.get(outputFolder), reportDate);
    }

    public void addInvoiceListener(Consumer<Invoice> listener) {
        invoiceListeners.add(listener);
    }

    public void removeInvoiceListener(Consumer<Invoice> listener) {
        invoiceListeners.remove(listener);
    }

    public Map<String, Customer> getCustomers() {
        return Collections.unmodifiableMap(customers);
    }

    public List<Invoice> getInvoices() {
        return Collections.unmodifiableList(invoices);
    }

    public Stream<Invoice> getInvoicesOrderedByNumber() {
        return invoices.stream().sorted(Comparator.comparingInt(Invoice::getNumber));
    }

    public void createCustomer(String firstName, String lastName, Terms terms) {
        Customer customer = new Customer(firstName, lastName, terms);
        customers.put(customer.getName(), customer);
        saveCustomers();
        reporter.reportCustomersAndVolume();
    }

    public void createInvoice(String customerName, double amount) {
        Customer customer = customers.get(customerName);
        if (customer != null) {
            int newInvoiceNumber = invoices.size() + 1;
            Invoice invoice = new Invoice(newInvoiceNumber, amount, LocalDate.now(), Optional.empty(), customer);
            invoices.add(invoice);
            saveInvoices();
            reporter.reportInvoicesOrderedByNumber();
            reporter.reportInvoicesGroupedByCustomer();
        }
    }

    public void payInvoice(int invoiceNumber) {
        invoices.stream()
                .filter(invoice -> invoice.getNumber() == invoiceNumber)
                .findFirst()
                .ifPresent(invoice -> {
                    invoice.setPaidDate(Optional.of(LocalDate.now()));
                    saveInvoices();
                    invoiceListeners.forEach(listener -> listener.accept(invoice));
                    reporter.reportOverdueInvoices();
                });
    }

    public void generateReports() {
        reporter.reportInvoicesOrderedByNumber();
        reporter.reportInvoicesGroupedByCustomer();
        reporter.reportOverdueInvoices();
        reporter.reportCustomersAndVolume();
    }

    private void saveCustomers() {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(customerFile)))) {
            parser.produceCustomers(customers.values().stream()).forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveInvoices() {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(invoiceFile)))) {
            parser.produceInvoices(invoices.stream()).forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
