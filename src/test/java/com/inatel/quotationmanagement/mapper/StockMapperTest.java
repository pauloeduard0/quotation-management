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

    private StockQuoteDto createStockQuoteDto(String stockId, LocalDate date, BigDecimal quoteValue) {
        return StockQuoteDto.builder()
                .stockId(stockId)
                .quotes(Collections.singletonMap(date, quoteValue))
                .build();
    }

    private StockQuote createStockQuote(String stockId, LocalDate date, BigDecimal quoteValue) {
        StockQuote stockQuote = StockQuote.builder()
                .id(UUID.randomUUID())
                .stockId(stockId)
                .quotes(new ArrayList<>())
                .build();

        Quote quote = Quote.builder()
                .date(date)
                .value(quoteValue)
                .build();

        stockQuote.addQuote(quote);

        return stockQuote;
    }

    @Test
    void givenStockQuoteDtoList_whenConvertToStockQuoteList_thenSizeShouldBeEqual() {
        List<StockQuoteDto> stockQuoteDtoList = new ArrayList<>();

        StockQuoteDto stockQuoteDto1 = createStockQuoteDto("petr4", LocalDate.now(), BigDecimal.valueOf(20L));
        stockQuoteDtoList.add(stockQuoteDto1);

        StockQuoteDto stockQuoteDto2 = createStockQuoteDto("aapl34", LocalDate.now(), BigDecimal.valueOf(40L));
        stockQuoteDtoList.add(stockQuoteDto2);

        List<StockQuote> stockQuoteList = StockMapper.toStockQuoteList(stockQuoteDtoList);

        assertEquals(stockQuoteDtoList.size(), stockQuoteList.size());
    }

    @Test
    void givenStockQuoteList_whenConvertToStockQuoteDtoList_thenAllFieldsShouldBeEqual() {
        List<StockQuote> stockQuoteList = new ArrayList<>();

        StockQuote stockQuote1 = createStockQuote("petr4", LocalDate.of(2023, 5, 9), BigDecimal.valueOf(23.0));
        stockQuoteList.add(stockQuote1);

        StockQuote stockQuote2 = createStockQuote("aapl34", LocalDate.of(2023, 5, 9), BigDecimal.valueOf(41.0));
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
    void givenStockQuoteDto_whenConvertToStockQuote_thenAllFieldsShouldBeEqual() {

        StockQuoteDto stockQuoteDto = createStockQuoteDto("petr4", LocalDate.now(), BigDecimal.valueOf(20L));

        StockQuote stockQuote = StockMapper.toStockQuote(stockQuoteDto);

        assertEquals(stockQuoteDto.id(), stockQuote.getId());
        assertEquals(stockQuoteDto.stockId(), stockQuote.getStockId());


        Map<LocalDate, BigDecimal> convertedQuotes = stockQuote.getQuotes().stream()
                .collect(Collectors.toMap(Quote::getDate, Quote::getValue));
        assertEquals(stockQuoteDto.quotes(), convertedQuotes);
    }

    @Test
    void givenStockQuote_whenConvertToStockQuoteDto_thenAllFieldsShouldBeEqual() {

        StockQuote stockQuote = createStockQuote("aapl34", LocalDate.of(2023, 5, 9), BigDecimal.valueOf(41.0));

        StockQuoteDto stockQuoteDto = StockMapper.toStockQuoteDto(stockQuote);

        assertEquals(stockQuote.getId(), stockQuoteDto.id());
        assertEquals(stockQuote.getStockId(), stockQuoteDto.stockId());

        Map<LocalDate, BigDecimal> convertedQuotes = stockQuoteDto.quotes();
        assertEquals(1, convertedQuotes.size());
        assertTrue(convertedQuotes.containsKey(LocalDate.of(2023, 5, 9)));
        assertEquals(BigDecimal.valueOf(41.0), convertedQuotes.get(LocalDate.of(2023, 5, 9)));
    }

}