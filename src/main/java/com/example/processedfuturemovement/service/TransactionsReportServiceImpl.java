package com.example.processedfuturemovement.service;

import com.example.processedfuturemovement.exceptions.TransactionsUnavailableException;
import com.example.processedfuturemovement.model.DailyTransactionReportGroup;
import com.example.processedfuturemovement.model.FutureTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.processedfuturemovement.utils.Constants.CSV_HEADERS;
import static java.lang.String.format;

@Service
public class TransactionsReportServiceImpl implements TransactionsReportService {
    private final TransactionsService transactionsService;
    public TransactionsReportServiceImpl(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }
    @Override
    public List<List<String>> generateDailySummaryData(String clientNumber)  {
        List<FutureTransaction> transactions = this.transactionsService.findTransactionsByClientId(clientNumber);
        if (transactions.isEmpty())
            throw new TransactionsUnavailableException(
                    format("Transactions unaivailable for client: %s . Please check the client number.", clientNumber)
            );

        Map<DailyTransactionReportGroup, Integer> transactionsForClient = transactions.stream()
                .collect(Collectors.groupingBy(futureTransaction ->
                                new DailyTransactionReportGroup(
                                        futureTransaction.getClientType(),
                                        futureTransaction.getClientNumber(),
                                        futureTransaction.getAccountNumber(),
                                        futureTransaction.getSubAccountNumber(),
                                        futureTransaction.getProductGroupCode(),
                                        futureTransaction.getExchangeCode(),
                                        futureTransaction.getSymbol(),
                                        futureTransaction.getExpirationDate()),
                        Collectors.summingInt(p -> p.getQuantityLong() - p.getQuantityShort())));

        if (transactionsForClient.isEmpty())  return null;

        return transactionsForClient.entrySet().stream().map(this::mapDailyTransactionsToReportRows).toList();
    }

    private List<String> mapDailyTransactionsToReportRows(Map.Entry<DailyTransactionReportGroup, Integer> futureTransactionEntry) {
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
