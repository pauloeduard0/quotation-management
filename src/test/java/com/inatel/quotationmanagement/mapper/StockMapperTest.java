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

        final StockQuoteDto stockQuoteDto1 = StockQuoteDto.builder()
                .stockId("petr4")
                .quotes(Collections.singletonMap(LocalDate.now(), BigDecimal.valueOf(20L)))
                .build();

        stockQuoteDtoList.add(stockQuoteDto1);

        final StockQuoteDto stockQuoteDto2 = StockQuoteDto.builder()
                .stockId("aapl34")
                .quotes(Collections.singletonMap(LocalDate.now(), BigDecimal.valueOf(40L)))
                .build();

        stockQuoteDtoList.add(stockQuoteDto2);

        List<StockQuote> stockQuoteList = StockMapper.toStockQuoteList(stockQuoteDtoList);

        assertEquals(stockQuoteDtoList.size(), stockQuoteList.size());
    }

    @Test
    void testToStockQuoteDtoList() {
        List<StockQuote> stockQuoteList = new ArrayList<>();

        StockQuote stockQuote1 = StockQuote.builder()
                .id(UUID.randomUUID())
                .stockId("petr4")
                .quotes(new ArrayList<>())
                .build();

        Quote quote1 = Quote.builder()
                .date(LocalDate.of(2023, 5, 9))
                .value(BigDecimal.valueOf(23.0))
                .build();
        stockQuote1.addQuote(quote1);

        stockQuoteList.add(stockQuote1);


        StockQuote stockQuote2 = StockQuote.builder()
                .id(UUID.randomUUID())
                .stockId("aapl34")
                .quotes(new ArrayList<>())
                .build();

        Quote quote2 = Quote.builder()
                .date(LocalDate.of(2023, 5, 9))
                .value(BigDecimal.valueOf(41.0))
                .build();

        stockQuote2.addQuote(quote2);

        stockQuoteList.add(stockQuote2);

        List<StockQuoteDto> stockQuoteDtoList = StockMapper.toStockQuoteDtoList(stockQuoteList);

        assertEquals(stockQuoteList.size(), stockQuoteDtoList.size());

        StockQuoteDto stockQuoteDto1 = stockQuoteDtoList.get(0);
        assertEquals(stockQuote1.getId(), stockQuoteDto1.id());
        assertEquals(stockQuote1.getStockId(), stockQuoteDto1.stockId());
        assertEquals(stockQuote1.getQuotes().size(), stockQuoteDto1.quotes().size());

        BigDecimal quoteValue1 = stockQuoteDto1.quotes().get(LocalDate.of(2023, 5, 9));
        assertEquals(BigDecimal.valueOf(23.0), quoteValue1);

        StockQuoteDto stockQuoteDto2 = stockQuoteDtoList.get(1);
        assertEquals(stockQuote2.getId(), stockQuoteDto2.id());
        assertEquals(stockQuote2.getStockId(), stockQuoteDto2.stockId());
        assertEquals(stockQuote2.getQuotes().size(), stockQuoteDto2.quotes().size());
        BigDecimal quoteValue2 = stockQuoteDto2.quotes().get(LocalDate.of(2023, 5, 9));
        assertEquals(BigDecimal.valueOf(41.0), quoteValue2);
    }

    @Test
    void testToStockQuote() {
        final StockQuoteDto stockQuoteDto = StockQuoteDto.builder()
                .id(UUID.randomUUID())
                .stockId("petr4")
                .quotes(Collections.singletonMap(LocalDate.now(), BigDecimal.valueOf(20L)))
                .build();

        StockQuote stockQuote = StockMapper.toStockQuote(stockQuoteDto);

        assertEquals(stockQuoteDto.id(), stockQuote.getId());
        assertEquals(stockQuoteDto.stockId(), stockQuote.getStockId());


        Map<LocalDate, BigDecimal> convertedQuotes = stockQuote.getQuotes().stream()
                .collect(Collectors.toMap(Quote::getDate, Quote::getValue));
        assertEquals(stockQuoteDto.quotes(), convertedQuotes);
    }

    @Test
    void testToStockQuoteDto() {

        StockQuote stockQuote = StockQuote.builder()
                .id(UUID.randomUUID())
                .stockId("aapl34")
                .quotes(new ArrayList<>())
                .build();

        Quote quote1 = Quote.builder()
                .date(LocalDate.of(2023, 5, 9))
                .value(BigDecimal.valueOf(41.0))
                .build();
        Quote quote2 = Quote.builder()
                .date(LocalDate.of(2023, 5, 10))
                .value(BigDecimal.valueOf(42.0))
                .build();
        stockQuote.addQuote(quote1);
        stockQuote.addQuote(quote2);

        StockQuoteDto stockQuoteDto = StockMapper.toStockQuoteDto(stockQuote);

        assertEquals(stockQuote.getId(), stockQuoteDto.id());
        assertEquals(stockQuote.getStockId(), stockQuoteDto.stockId());

        Map<LocalDate, BigDecimal> convertedQuotes = stockQuoteDto.quotes();
        assertEquals(2, convertedQuotes.size());
        assertTrue(convertedQuotes.containsKey(LocalDate.of(2023, 5, 9)));
        assertEquals(BigDecimal.valueOf(41.0), convertedQuotes.get(LocalDate.of(2023, 5, 9)));
        assertTrue(convertedQuotes.containsKey(LocalDate.of(2023, 5, 10)));
        assertEquals(BigDecimal.valueOf(42.0), convertedQuotes.get(LocalDate.of(2023, 5, 10)));
    }

}