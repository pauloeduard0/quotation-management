package com.inatel.quotationmanagement.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StockQuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void testGetQuotes_ReturnsStockQuotesByStockId() throws Exception {

        String stockId = "petr4";

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/quote")
                        .param("stockId", stockId))
                .andExpect(status().isOk())
                .andReturn();

        final String responseBody = mvcResult.getResponse().getContentAsString();
        final List<StockQuoteDto> stockQuotes = mapper.readValue(responseBody, new TypeReference<List<StockQuoteDto>>() {
        });

        assertThat(stockQuotes).isNotEmpty();
    }

    @Test
    void testGetQuotes_ReturnsAllStockQuotes() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/quote"))
                .andExpect(status().isOk())
                .andReturn();

        final String responseBody = mvcResult.getResponse().getContentAsString();
        final List<StockQuoteDto> stockQuotes = mapper.readValue(responseBody, new TypeReference<List<StockQuoteDto>>() {
        });

        assertThat(stockQuotes).isNotEmpty();
    }

    @Test
    void testCreateNewStockQuote_ReturnsCorrectJson() throws Exception {
        final StockQuoteDto stockQuoteDto = StockQuoteDto.builder()
                .stockId("petr4")
                .quotes(Collections.singletonMap(LocalDate.now(), BigDecimal.valueOf(20L)))
                .build();
        final MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/quote")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(stockQuoteDto)))
                .andExpect(status().isCreated())
                .andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        final String id = jsonObject.getString("id");
        assertThat(id).isNotNull().isInstanceOf(String.class);
        final String stockId = jsonObject.getString("stockId");
        assertThat(stockId).isEqualTo(stockQuoteDto.stockId());
        final JSONObject quotes = jsonObject.getJSONObject("quotes");
        assertThat(quotes.length()).isEqualTo(1);
        final BigDecimal quoteValue = new BigDecimal(quotes.getString(LocalDate.now().toString()));
        assertThat(quoteValue).isEqualTo(stockQuoteDto.quotes().get(LocalDate.now()));
    }

}
