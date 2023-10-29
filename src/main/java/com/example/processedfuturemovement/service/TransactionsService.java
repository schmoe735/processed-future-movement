package com.example.processedfuturemovement.service;

import com.example.processedfuturemovement.model.FutureTransaction;

import java.util.List;

public interface TransactionsService {
    List<FutureTransaction> findTransactionsByClientId(String clientId);
}
