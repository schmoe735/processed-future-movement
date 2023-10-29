package com.example.processedfuturemovement.service;

import com.example.processedfuturemovement.model.FutureTransaction;
import com.example.processedfuturemovement.utils.CsvHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionReportServiceTest {

    @MockBean
    private TransactionsService transactionsService;


    @Autowired
    TransactionsReportService transactionsReportService;
    @Test
    public void shouldGenerateReportByGroupingDataForClient() {
        List<FutureTransaction> futureTransactions = new ArrayList<>();
        futureTransactions.add(new FutureTransaction("CL", "4321", "1234", "0002", "FU", "SGX", "NK", 2, 1, LocalDate.of(2020,1, 1)));
        futureTransactions.add(new FutureTransaction("CL", "4321", "1234", "0002", "FU", "SGX", "NK", 3, 0, LocalDate.of(2020,1, 1)));
        when(transactionsService.findTransactionsByClientId(anyString())).thenReturn(futureTransactions);
        List<List<String>> csvData = transactionsReportService.generateDailySummaryData("4321");
        ByteArrayInputStream in = CsvHelper.futureTransactionsToCSV(csvData);
        String s = new String(in.readAllBytes());
        assertThat(s).contains("CL,4321,1234,0002,SGX,FU,NK,2020-01-01,4");

    }
}
