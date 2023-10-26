package com.example.processedfuturemovement.service;

import java.io.ByteArrayInputStream;

public interface TransactionReportService {
    ByteArrayInputStream generateReport(String clientNumber) ;
}
