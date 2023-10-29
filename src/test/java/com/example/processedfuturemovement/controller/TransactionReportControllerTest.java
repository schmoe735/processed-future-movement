package com.example.processedfuturemovement.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionReportControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void shouldReturnDownloadCSVForClientId() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/report/daily-summary/1234?output=csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/csv"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotEmpty();
    }
}
