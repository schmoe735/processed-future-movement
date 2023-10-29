package com.example.processedfuturemovement.service;

import java.util.List;

public interface TransactionsReportService {

    List<List<String>> generateDailySummaryData(String clientNumber) ;
}
