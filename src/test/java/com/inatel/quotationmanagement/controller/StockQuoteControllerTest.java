package com.inatel.quotationmanagement.controller;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.repository.StockRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class StockQuoteControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StockRepository stockRepository;

    private StockQuoteDto createStockQuoteDto(String stockId, LocalDate date, BigDecimal quoteValue) {
        return StockQuoteDto.builder()
                .stockId(stockId)
                .quotes(Collections.singletonMap(date, quoteValue))
                .build();
    }

    @BeforeEach
    void setup() {

        StockQuoteDto stockQuoteDto = createStockQuoteDto("petr4", LocalDate.now(), BigDecimal.valueOf(20L));

        webTestClient.post()
                .uri("/quote")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(stockQuoteDto)
                .exchange();

        StockQuoteDto stockQuoteDto2 = createStockQuoteDto("aapl34", LocalDate.now(), BigDecimal.valueOf(40L));

        webTestClient.post()
                .uri("/quote")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(stockQuoteDto2)
                .exchange();
    }

    @AfterEach
    void cleanup() {

        stockRepository.deleteAll();
    }

    @Test
    void givenStockQuoteDto_whenSaveQuote_thenReturnHttpStatus201AndSavedStockQuoteDto() {

        StockQuoteDto stockQuoteDto = createStockQuoteDto("petr4", LocalDate.now(), BigDecimal.valueOf(20L));

        webTestClient.post()
                .uri("/quote")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(stockQuoteDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.stockId").isEqualTo("petr4")
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
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.title").value(equalTo("Invalid Format Exception"))
                .jsonPath("$.type").value(equalTo("https://api.quotationmanagement.com/errors/bad-request"));
    }

    @Test
    void givenExistingStockId_whenGetQuotes_thenReturnStockQuotesList() {

        webTestClient.get()
                .uri("/quote/{stockId}", "petr4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].stockId").isEqualTo("petr4")
                .jsonPath("$[0].quotes").isNotEmpty();

    }

    @Test
    void givenNoParameters_whenGetAllStockQuote_thenReturnHttpStatus200StockQuoteDtoList() {

        webTestClient.get()
                .uri("/quote")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content[?(@.stockId == 'petr4' || @.stockId == 'aapl34')]").exists()
                .jsonPath("$.content[?(@.quotes)].stockId").value(containsInAnyOrder("petr4", "aapl34"))
                .jsonPath("$.content[0].quotes").isNotEmpty();
    }

}