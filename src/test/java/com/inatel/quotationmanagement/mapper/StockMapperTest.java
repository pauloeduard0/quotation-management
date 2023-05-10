package com.inatel.quotationmanagement.mapper;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.Quote;
import com.inatel.quotationmanagement.model.entities.StockQuote;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StockMapperTest {

    @Test
    void testToStockQuoteList() {
        List<StockQuoteDto> stockQuoteDtoList = new ArrayList<>();

        UUID id1 = UUID.randomUUID();
        String stockId1 = "petr4";
        Map<LocalDate, BigDecimal> quotes1 = new HashMap<>();
        quotes1.put(LocalDate.of(2023, 5, 9), BigDecimal.valueOf(23.0));
        quotes1.put(LocalDate.of(2023, 5, 10), BigDecimal.valueOf(24.00));
        StockQuoteDto stockQuoteDto1 = new StockQuoteDto(id1, stockId1, quotes1);

        stockQuoteDtoList.add(stockQuoteDto1);

        UUID id2 = UUID.randomUUID();
        String stockId2 = "aapl34";
        Map<LocalDate, BigDecimal> quotes2 = new HashMap<>();
        quotes2.put(LocalDate.of(2023, 5, 9), BigDecimal.valueOf(41.0));
        quotes2.put(LocalDate.of(2023, 5, 10), BigDecimal.valueOf(42.0));
        StockQuoteDto stockQuoteDto2 = new StockQuoteDto(id2, stockId2, quotes2);

        stockQuoteDtoList.add(stockQuoteDto2);

        List<StockQuote> stockQuoteList = StockMapper.toStockQuoteList(stockQuoteDtoList);

        assertEquals(stockQuoteDtoList.size(), stockQuoteList.size());
    }

    @Test
    void testToStockQuoteDtoList() {
        List<StockQuote> stockQuoteList = new ArrayList<>();

        UUID id1 = UUID.randomUUID();
        String stockId1 = "petr4";
        StockQuote stockQuote1 = StockQuote.builder()
                .id(id1)
                .stockId(stockId1)
                .quotes(new ArrayList<>())
                .build();

        Quote quote1_1 = Quote.builder()
                .date(LocalDate.of(2023, 5, 9))
                .value(BigDecimal.valueOf(23.0))
                .build();
        Quote quote1_2 = Quote.builder()
                .date(LocalDate.of(2023, 5, 10))
                .value(BigDecimal.valueOf(24.0))
                .build();
        stockQuote1.addQuote(quote1_1);
        stockQuote1.addQuote(quote1_2);

        stockQuoteList.add(stockQuote1);

        UUID id2 = UUID.randomUUID();
        String stockId2 = "aapl34";
        StockQuote stockQuote2 = StockQuote.builder()
                .id(id2)
                .stockId(stockId2)
                .quotes(new ArrayList<>())
                .build();

        Quote quote2_1 = Quote.builder()
                .date(LocalDate.of(2023, 5, 9))
                .value(BigDecimal.valueOf(41.0))
                .build();
        Quote quote2_2 = Quote.builder()
                .date(LocalDate.of(2023, 5, 10))
                .value(BigDecimal.valueOf(42.0))
                .build();
        stockQuote2.addQuote(quote2_1);
        stockQuote2.addQuote(quote2_2);

        stockQuoteList.add(stockQuote2);

        List<StockQuoteDto> stockQuoteDtoList = StockMapper.toStockQuoteDtoList(stockQuoteList);

        assertEquals(stockQuoteList.size(), stockQuoteDtoList.size());
    }

    @Test
    void testToStockQuote() {
        UUID id = UUID.randomUUID();
        String stockId = "petr4";
        Map<LocalDate, BigDecimal> quotes1 = new HashMap<>();
        quotes1.put(LocalDate.of(2023, 5, 9), BigDecimal.valueOf(23.0));
        quotes1.put(LocalDate.of(2023, 5, 10), BigDecimal.valueOf(24.0));
        StockQuoteDto stockQuoteDto = new StockQuoteDto(id, stockId, quotes1);

        StockQuote stockQuote = StockMapper.toStockQuote(stockQuoteDto);

        assertEquals(id, stockQuote.getId());
        assertEquals(stockId, stockQuote.getStockId());


        Map<LocalDate, BigDecimal> convertedQuotes = stockQuote.getQuotes().stream()
                .collect(Collectors.toMap(Quote::getDate, Quote::getValue));
        assertEquals(quotes1, convertedQuotes);
    }

    @Test
    void testToStockQuoteDto() {
        UUID id1 = UUID.randomUUID();
        String stockId1 = "aapl34";
        StockQuote stockQuote = StockQuote.builder()
                .id(id1)
                .stockId(stockId1)
                .quotes(new ArrayList<>())
                .build();

        Quote quote1_1 = Quote.builder()
                .date(LocalDate.of(2023, 5, 9))
                .value(BigDecimal.valueOf(41.0))
                .build();
        Quote quote1_2 = Quote.builder()
                .date(LocalDate.of(2023, 5, 10))
                .value(BigDecimal.valueOf(42.0))
                .build();
        stockQuote.addQuote(quote1_1);
        stockQuote.addQuote(quote1_2);

        StockQuoteDto stockQuoteDto = StockMapper.toStockQuoteDto(stockQuote);

        assertEquals(id1, stockQuoteDto.id());
        assertEquals(stockId1, stockQuoteDto.stockId());

        Map<LocalDate, BigDecimal> convertedQuotes = stockQuoteDto.quotes();
        assertEquals(2, convertedQuotes.size());
        assertTrue(convertedQuotes.containsKey(LocalDate.of(2023, 5, 9)));
        assertEquals(BigDecimal.valueOf(41.0), convertedQuotes.get(LocalDate.of(2023, 5, 9)));
        assertTrue(convertedQuotes.containsKey(LocalDate.of(2023, 5, 10)));
        assertEquals(BigDecimal.valueOf(42.0), convertedQuotes.get(LocalDate.of(2023, 5, 10)));
    }

}