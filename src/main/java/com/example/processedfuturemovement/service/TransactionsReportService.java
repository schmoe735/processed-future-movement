package com.example.processedfuturemovement.service;

import java.io.ByteArrayInputStream;

public interface TransactionsReportService {

    ByteArrayInputStream generateReport(String clientNumber) ;
}
