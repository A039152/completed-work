package com.amica.billing.parse;

import com.amica.billing.Customer;
import com.amica.billing.Invoice;
import com.amica.billing.Terms;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

/**
 * A parser that can read a specific flat file format.
 * 
 * @author Will Provost
 */
 @Log
public class FlatParser implements com.amica.billing.parse.Parser {
	public static final int CUSTOMER_FIRST_NAME_OFFSET = 0;
	public static final int CUSTOMER_FIRST_NAME_LENGTH = 12;
	public static final int CUSTOMER_LAST_NAME_OFFSET = CUSTOMER_FIRST_NAME_OFFSET + CUSTOMER_FIRST_NAME_LENGTH;
	public static final int CUSTOMER_LAST_NAME_LENGTH = 12;
	public static final int CUSTOMER_TERMS_OFFSET = CUSTOMER_LAST_NAME_OFFSET + CUSTOMER_LAST_NAME_LENGTH;
	public static final int CUSTOMER_TERMS_LENGTH = 10;
	public static final int CUSTOMER_LENGTH = CUSTOMER_TERMS_OFFSET + CUSTOMER_TERMS_LENGTH;

	@Override
	public Stream<Customer> parseCustomers(Stream<String> customerLines) {
		return customerLines.map(line -> {
			if (line.length() >= CUSTOMER_LENGTH) {
				try {
					String firstName = line.substring(CUSTOMER_FIRST_NAME_OFFSET, CUSTOMER_LAST_NAME_OFFSET).trim();
					String lastName = line.substring(CUSTOMER_LAST_NAME_OFFSET, CUSTOMER_TERMS_OFFSET).trim();
					String termsString = line.substring(CUSTOMER_TERMS_OFFSET, CUSTOMER_LENGTH).trim();
					return new Customer(firstName, lastName, Terms.valueOf(termsString));
				} catch (Exception e) {
					log.warning("Failed to parse customer line: " + line);
					return null;
				}
			}
			return null;
		}).filter(Objects::nonNull);
	}

	@Override
	public Stream<Invoice> parseInvoices(Stream<String> invoiceLines, Map<String, Customer> customers) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyy");
		return invoiceLines.map(line -> {
			try {
				int number = Integer.parseInt(line.substring(0, 4).trim());
				String firstName = line.substring(4, 16).trim();
				String lastName = line.substring(16, 28).trim();
				double amount = Double.parseDouble(line.substring(28, 36).trim());
				LocalDate invoiceDate = LocalDate.parse(line.substring(36, 42), formatter);
				String paidString = line.substring(42).trim();
				Optional<LocalDate> paidDate = paidString.isEmpty() ? Optional.empty() : Optional.of(LocalDate.parse(paidString, formatter));
				Customer customer = customers.get(firstName + " " + lastName);
				return new Invoice(number, amount, invoiceDate, paidDate, customer);
			} catch (Exception e) {
				log.warning("Failed to parse invoice line: " + line);
				return null;
			}
		}).filter(Objects::nonNull);
	}

	@Override
	public Stream<String> produceCustomers(Stream<Customer> customers) {
		return customers.map(customer -> String.format("%-12s%-12s%-10s", customer.getFirstName(), customer.getLastName(), customer.getTerms()));
	}

	@Override
	public Stream<String> produceInvoices(Stream<Invoice> invoices) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyy");
		return invoices.map(invoice -> String.format("%04d%-12s%-12s%8.2f%s%s",
				invoice.getNumber(),
				invoice.getCustomer().getFirstName(),
				invoice.getCustomer().getLastName(),
				invoice.getAmount(),
				invoice.getInvoiceDate().format(formatter),
				invoice.getPaidDate().map(date -> date.format(formatter)).orElse("")));
	}
}
