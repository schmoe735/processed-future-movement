package com.example.processedfuturemovement.utils;

import com.example.processedfuturemovement.model.DailyTransactionReportGroup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.processedfuturemovement.utils.Constants.CSV_HEADERS;

public class CsvHelper {


    public static ByteArrayInputStream futureTransactionsToCSV(Map<DailyTransactionReportGroup, Integer> futureTransactions) {
        final CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(CSV_HEADERS)
                .build();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (Map.Entry<DailyTransactionReportGroup, Integer> futureTransactionEntry : futureTransactions.entrySet()) {
                List<String> data = getTransactions(futureTransactionEntry);

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

    private static List<String> getTransactions(Map.Entry<DailyTransactionReportGroup, Integer> futureTransactionEntry) {
        DailyTransactionReportGroup futureTransaction = futureTransactionEntry.getKey();
        return Arrays.asList(
                String.valueOf(futureTransaction.getClientType()),
                futureTransaction.getClientNumber(),
                futureTransaction.getAccountNumber(),
                futureTransaction.getSubAccountNumber(),
                futureTransaction.getExchangeCode(),
                futureTransaction.getProductGroupCode(),
                futureTransaction.getSymbol(),
                futureTransaction.getExpirationDate().toString(),
                String.valueOf(futureTransactionEntry.getValue())
        );
    }
}
