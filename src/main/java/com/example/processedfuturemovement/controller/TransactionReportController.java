package com.example.processedfuturemovement.controller;

import com.example.processedfuturemovement.exceptions.TransactionsUnavailableException;
import com.example.processedfuturemovement.service.TransactionsReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

import static com.example.processedfuturemovement.utils.Constants.OUTPUT_CSV;

@Slf4j
@RestController
@RequestMapping("/api/v1/report")
public class TransactionReportController {
    final TransactionsReportService transactionsReportService;
    public TransactionReportController(TransactionsReportService transactionsReportService) {
        this.transactionsReportService = transactionsReportService;
    }

    /**
     * Generate a daily summary report for a client.
     * #TODO pickup client number from a jwt after security implementation
     * @param clientNumber client number for the report
     * @return CSV output file with a report of total number of transactions by a client for a product
     */
    @GetMapping("/daily-summary/{clientNumber}")
    public ResponseEntity<Resource> getFile(@PathVariable("clientNumber") String clientNumber)  {
        ByteArrayInputStream csvOutput = transactionsReportService.generateReport(clientNumber);
        if (csvOutput == null) throw new TransactionsUnavailableException("Transaction data unavailable");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + OUTPUT_CSV)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(csvOutput));
    }
}
