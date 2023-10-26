package com.example.processedfuturemovement.exceptions;

public class TransactionsUnavailableException extends RuntimeException{
    public TransactionsUnavailableException(String message) {
        super(message);
    }
}
