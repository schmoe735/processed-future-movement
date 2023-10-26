package com.example.processedfuturemovement.service;

import com.example.processedfuturemovement.model.DailyTransactionReportGroup;
import com.example.processedfuturemovement.model.FutureTransaction;
import com.example.processedfuturemovement.utils.CsvHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class TransactionsReportServiceImpl implements TransactionsReportService {
    private final TransactionsService transactionsService;
    public TransactionsReportServiceImpl(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }
    @Override
    public ByteArrayInputStream generateReport(String clientNumber)  {
        List<FutureTransaction> transactions = this.transactionsService.loadTransactions();
        if (transactions.isEmpty()) return null;

        Map<DailyTransactionReportGroup, Integer> transactionsForClient = transactions.stream()
                .filter(futureTransaction -> futureTransaction.getClientNumber().equals(clientNumber))
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

        return CsvHelper.futureTransactionsToCSV(transactionsForClient);
    }
}
