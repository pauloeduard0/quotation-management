package com.inatel.quotationmanagement.controller;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.service.StockQuoteService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Collections;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class StockQuoteControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StockQuoteService stockQuoteService;

    @Test
    void givenStockQuoteDto_whenSaveQuote_thenReturnHttpStatus201AndSavedStockQuoteDto() {

        StockQuoteDto stockQuoteDto = StockQuoteDto.builder()
                .stockId("petr4")
                .quotes(Collections.singletonMap(LocalDate.now(), BigDecimal.valueOf(20L)))
                .build();

        webTestClient.post()
                .uri("/quote")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(stockQuoteDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.stockId").isEqualTo(stockQuoteDto.stockId())
                .jsonPath("$.quotes").isNotEmpty();
    }

    @Test
    void givenInvalidFormatStockQuoteDto_whenSaveQuote_thenReturnHttpStatus400() {

        webTestClient.post()
                .uri("/quote")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("{ \"stockId\": \"Petr4\", \"quotes\": { \"2023-05-15\": \"invalid\" } }")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void givenStockId_whenGetQuotes_thenReturnHttpStatus200AndEmptyStockQuoteDtoList() {

        webTestClient.get()
                .uri("/quote/{stockId}", "petr4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isArray();
    }
    
}
