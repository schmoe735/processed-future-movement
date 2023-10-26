package com.example.processedfuturemovement.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class DailyTransactionReportGroup {
    String clientType;
    String clientNumber;
    String accountNumber;
    String subAccountNumber;
    String productGroupCode;
    String exchangeCode;
    String symbol;
    LocalDate expirationDate;
}
