package com.example.processedfuturemovement.service;

import com.example.processedfuturemovement.model.FutureTransaction;

import java.util.List;

public interface TransactionCacheService {
    /**
     * Load transactions into in memory cache due to absence of a database to reduce IO
     * @return list of transaction objects
     */
    List<FutureTransaction> loadTransactions();
}
