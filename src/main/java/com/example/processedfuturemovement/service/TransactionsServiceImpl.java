package com.example.processedfuturemovement.service;

import com.example.processedfuturemovement.model.FutureTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TransactionsServiceImpl implements TransactionsService {
    private final TransactionCacheService transactionCacheService;

    public TransactionsServiceImpl(TransactionCacheService transactionCacheService) {
        this.transactionCacheService = transactionCacheService;
    }

    @Override
    public List<FutureTransaction> findTransactionsByClientId(String clientNumber) {
        return transactionCacheService.loadTransactions().stream()
                .filter(futureTransaction -> futureTransaction.getClientNumber().equals(clientNumber))
                .toList();
    }
}
