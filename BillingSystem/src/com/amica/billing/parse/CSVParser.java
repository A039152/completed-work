package com.amica.billing.parse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.amica.billing.Customer;
import com.amica.billing.Invoice;

import com.amica.billing.Terms;
import lombok.extern.java.Log;

/**
 * A parser that can read a CSV format with certain expected columns.
 * 
 * @author Will Provost
 */
@Log
public class CSVParser implements Parser {

	public static final int CUSTOMER_COLUMNS = 3;
	public static final int CUSTOMER_FIRST_NAME_COLUMN = 0;
	public static final int CUSTOMER_LAST_NAME_COLUMN = 1;
	public static final int CUSTOMER_TERMS_COLUMN = 2;

	public static final int INVOICE_MIN_COLUMNS = 5;
	public static final int INVOICE_NUMBER_COLUMN = 0;
	public static final int INVOICE_FIRST_NAME_COLUMN = 1;
	public static final int INVOICE_LAST_NAME_COLUMN = 2;
	public static final int INVOICE_AMOUNT_COLUMN = 3;
	public static final int INVOICE_DATE_COLUMN = 4;
	public static final int INVOICE_PAID_DATE_COLUMN = 5;

	/**
	 * Helper that can parse one line of comma-separated text in order to
	 * produce a {@link Customer} object.
	 */
	@Override
	public Stream<Customer> parseCustomers(Stream<String> customerLines) {
		return customerLines.map(line -> {
			String[] fields = line.split(",");
			try {
				String firstName = fields[0].trim();
				String lastName = fields[1].trim();
				String termsString = fields[2].trim();

				Terms terms = mapTerms(termsString);

				return new Customer(firstName, lastName, terms);
			} catch (Exception e) {
				log.warning("Failed to parse customer line: " + line);
				return null;
			}
		}).filter(Objects::nonNull);
	}

	private Terms mapTerms(String terms) {
		switch (terms) {
			case "CASH":
				return Terms.CASH;
			case "30":
				return Terms.CREDIT_30;
			case "45":
				return Terms.CREDIT_45;
			case "60":
				return Terms.CREDIT_60;
			case "90":
				return Terms.CREDIT_90;
			default:
				throw new IllegalArgumentException("Invalid terms value: " + terms);
		}
	}

	@Override
	public Stream<Invoice> parseInvoices(Stream<String> invoiceLines, Map<String, Customer> customers) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return invoiceLines.map(line -> {
			try {
				String[] fields = line.split(",");
				Customer customer = customers.get(fields[1] + " " + fields[2]);
				return new Invoice(
						Integer.parseInt(fields[0]),
						Double.parseDouble(fields[3]),
						LocalDate.parse(fields[4], formatter),
						fields.length > 5 ? Optional.of(LocalDate.parse(fields[5], formatter)) : Optional.empty(),
						customer);
			} catch (Exception e) {
				log.warning("Failed to parse invoice line: " + line);
				return null;
			}
		}).filter(Objects::nonNull);
	}

	@Override
	public Stream<String> produceCustomers(Stream<Customer> customers) {
		return customers.map(customer -> String.format("%s,%s,%s",
				customer.getFirstName(), customer.getLastName(), customer.getTerms()));
	}

	@Override
	public Stream<String> produceInvoices(Stream<Invoice> invoices) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return invoices.map(invoice -> String.format("%d,%s,%s,%.2f,%s,%s",
				invoice.getNumber(),
				invoice.getCustomer().getFirstName(),
				invoice.getCustomer().getLastName(),
				invoice.getAmount(),
				invoice.getInvoiceDate().format(formatter),
				invoice.getPaidDate().map(date -> date.format(formatter)).orElse("")));
	}

	/**
	 * Helper to write a CSV representation of one customer.
	 */
	public String formatCustomer(Customer customer) {
		//TODO provide the values to be formatted
		return String.format("%s,%s,%s", "NYI", "NYI", "NYI");
	}
	
	/**
	 * Helper to write a CSV representation of one invoice.
	 */
	public String formatInvoice(Invoice invoice) {
		//TODO provide the values to be formatted
		return String.format("%d,%s,%s,%.2f,%s%s", 
				0, "NYI", "NYI", 0.0, "NYI", "NYI");
	}
}
