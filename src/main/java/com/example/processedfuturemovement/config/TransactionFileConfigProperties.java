package com.example.processedfuturemovement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "transactions-file")
public class TransactionFileConfigProperties {
    private Map<String, Map<String, Integer>> indexes;
}
