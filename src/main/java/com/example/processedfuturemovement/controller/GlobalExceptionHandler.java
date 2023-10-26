package com.example.processedfuturemovement.controller;

import com.example.processedfuturemovement.exceptions.FileParseException;
import com.example.processedfuturemovement.exceptions.TransactionsUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({FileParseException.class})
    public ResponseEntity<Object> handleFileParseException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while reading transaction data. Please try again later.");
    }
    @ExceptionHandler({TransactionsUnavailableException.class})
    public ResponseEntity<Object> handleTransactionsUnavailableException(TransactionsUnavailableException exception) {
        log.info("Transaction data not available", exception);
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
