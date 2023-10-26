package com.example.processedfuturemovement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FutureTransaction implements Serializable {
    String clientType;
    String clientNumber;
    String accountNumber;
    String subAccountNumber;
    String productGroupCode;
    String exchangeCode;
    String symbol;
    Integer quantityLong;
    Integer quantityShort;
    LocalDate expirationDate;
}
