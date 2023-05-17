package com.inatel.quotationmanagement.controller;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.service.StockQuoteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Collections;


@ExtendWith(SpringExtension.class)
@WebMvcTest(StockQuoteController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StockQuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockQuoteService stockQuoteService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    void givenNoParameters_whenGetAllStockQuote_thenReturnHttpStatus200AndEmptyStockQuoteDtoList() throws Exception {

        Mockito.when(stockQuoteService.getAllStockQuote(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/quote")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(0));
    }

    @Test
    void givenStockId_whenGetQuotes_thenReturnHttpStatus200AndEmptyStockQuoteDtoList() throws Exception {

        Mockito.when(stockQuoteService.getStockQuoteByStockId(Mockito.eq("petr4"), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/quote/{stockId}", "petr4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(0));
    }

    @Test
    void givenStockQuoteDto_whenSaveQuote_thenReturnHttpStatus201AndSavedStockQuoteDto() throws Exception {

        StockQuoteDto stockQuoteDto = StockQuoteDto.builder()
                .stockId("petr4")
                .quotes(Collections.singletonMap(LocalDate.now(), BigDecimal.valueOf(20L)))
                .build();
        Mockito.when(stockQuoteService.saveStockQuote(Mockito.any(StockQuoteDto.class)))
                .thenReturn(stockQuoteDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/quote")
                        .content("{ \"stockId\": \"petr4\", \"quotes\": { \"2023-05-15\": 20 } }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockId").value("petr4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quotes").isNotEmpty());
    }

    @Test
    void givenInvalidFormatStockQuoteDto_whenSaveQuote_thenReturnHttpStatus400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/quote")
                        .content("{ \"stockId\": \"Petr4\", \"quotes\": { \"2023-05-15\": \"invalid\" } }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
