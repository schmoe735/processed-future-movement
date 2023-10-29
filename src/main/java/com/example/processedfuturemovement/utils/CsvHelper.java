package com.example.processedfuturemovement.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.processedfuturemovement.utils.Constants.CSV_HEADERS;

public class CsvHelper {
    public static ByteArrayInputStream futureTransactionsToCSV(List<List<String>> csvData) {
        final CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(CSV_HEADERS)
                .build();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (List<String> row : csvData) {
                csvPrinter.printRecord(row);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
