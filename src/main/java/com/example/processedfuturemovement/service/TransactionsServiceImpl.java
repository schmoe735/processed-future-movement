package com.example.processedfuturemovement.service;

import com.example.processedfuturemovement.config.TransactionFileConfigProperties;
import com.example.processedfuturemovement.exceptions.FileParseException;
import com.example.processedfuturemovement.model.FutureTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.example.processedfuturemovement.utils.Constants.*;

@Slf4j
@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Value("${inputfile}")
    private String inputfile;

    private final  Map<String, Map<String, Integer>> transactionFilePropertiesConfig;

    public TransactionsServiceImpl(TransactionFileConfigProperties transactionFileConfigProperties) {
        this.transactionFilePropertiesConfig = transactionFileConfigProperties.getIndexes();
    }

    @Override
    @Cacheable(value = "transactions")
    public List<FutureTransaction> loadTransactions()  {
        try {
            List<String> transactions = Files.readAllLines(Paths.get(inputfile));
            return transactions.stream()
                    .map(mapTransactionFileRowToBean()).toList();
        } catch (IOException ioException) {
            log.error("Error while parsing daily transactions file", ioException);
            throw new FileParseException("Error while reading daily transaction input file", ioException);
        } catch (Exception exception) {
            log.error("Unknown error while parsing daily transactions", exception);
            throw new RuntimeException("Errors while parsing daily transactions", exception);
        }
    }

    /**
     * Map a single row of transaction file to FutureTransaction class object
     * @return mapper function to process mapping a transaction row
     */
    private Function<String, FutureTransaction> mapTransactionFileRowToBean() {
        return row -> {
            FutureTransaction futureTransaction = new FutureTransaction();
            futureTransaction.setClientType(getSubstringValue(row, CLIENT_TYPE));
            futureTransaction.setClientNumber(getSubstringValue(row, CLIENT_NUMBER));
            futureTransaction.setAccountNumber(getSubstringValue(row, ACCOUNT_NUMBER));
            futureTransaction.setSubAccountNumber(getSubstringValue(row, SUB_ACCOUNT_NUMBER));
            futureTransaction.setExchangeCode(getSubstringValue(row, EXCHANGE_CODE));
            futureTransaction.setProductGroupCode(getSubstringValue(row, PRODUCT_GROUP_CODE));
            futureTransaction.setSymbol(getSubstringValue(row, SYMBOL));
            futureTransaction.setExpirationDate(LocalDate.parse(getSubstringValue(row, EXPIRATION_DATE), DateTimeFormatter.ofPattern(INPUT_FILE_DATE_FORMAT)));
            futureTransaction.setQuantityLong(Integer.parseInt(getSubstringValue(row, QUANTITY_LONG)));
            futureTransaction.setQuantityShort(Integer.parseInt(getSubstringValue(row, QUANTITY_SHORT)));
            return futureTransaction;
        };
    }

    private String getSubstringValue(String row, String configKey) {
        Map<String, Integer> configValue = transactionFilePropertiesConfig.get(configKey);
        return row.substring(configValue.get(START) - 1, configValue.get(END)).trim();
    }
}
