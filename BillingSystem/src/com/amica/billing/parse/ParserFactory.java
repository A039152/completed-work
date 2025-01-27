package com.amica.billing.parse;

public class ParserFactory {
    public static Parser createParser(String filename) {
        if (filename.endsWith(".csv")) {
            return new CSVParser();
        } else if (filename.endsWith(".flat")) {
            return new FlatParser();
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + filename);
        }
    }
}
