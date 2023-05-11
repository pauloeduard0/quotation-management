package com.inatel.quotationmanagement.service;

import com.inatel.quotationmanagement.adapter.StockAdapter;
import com.inatel.quotationmanagement.exception.StockNotFoundException;
import com.inatel.quotationmanagement.mapper.StockMapper;
import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.StockQuote;
import com.inatel.quotationmanagement.model.rest.Stock;
import com.inatel.quotationmanagement.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockQuoteServiceTest {

    @Mock
    private StockAdapter stockAdapter;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockQuoteService stockQuoteService;

    @Test
    void testSaveStockQuote_ValidStock_ReturnsStockQuoteDto() {

        final StockQuoteDto stockQuoteDto = StockQuoteDto.builder()
                .stockId("petr4")
                .quotes(Collections.singletonMap(LocalDate.now(), BigDecimal.valueOf(20L)))
                .build();

        final Stock stock = Stock.builder()
                .id("petr4")
                .description("Stock petr4")
                .build();

        when(stockAdapter.getAllStock()).thenReturn(Arrays.asList(stock));

        when(stockRepository.save(Mockito.any(StockQuote.class))).thenReturn(StockMapper.toStockQuote(stockQuoteDto));

        StockQuoteDto result = stockQuoteService.saveStockQuote(stockQuoteDto);

        assertNotNull(result);
        assertEquals(stockQuoteDto.id(), result.id());
        assertEquals(stockQuoteDto.stockId(), result.stockId());
        assertEquals(stockQuoteDto.quotes().size(), result.quotes().size());
        assertTrue(result.quotes().containsKey(LocalDate.now()));
        assertEquals(BigDecimal.valueOf(20L), result.quotes().get(LocalDate.now()));
    }


    @Test
    void testSaveStockQuote_InvalidStock_ThrowsStockNotFoundException() {
        final StockQuoteDto stockQuoteDto = StockQuoteDto.builder()
                .id(UUID.randomUUID())
                .stockId("petr4")
                .quotes(Collections.emptyMap())
                .build();
        when(stockAdapter.getAllStock()).thenReturn(Collections.emptyList());

        assertThrows(StockNotFoundException.class, () -> {
            stockQuoteService.saveStockQuote(stockQuoteDto);
        });
    }

    @Test
    void testGetStockQuoteByStockId_ValidStockId_ReturnsStockQuoteDtoList() {
        // Arrange
        String stockId = "petr4";
        final StockQuote stockQuote1 = StockQuote.builder()
                .id(UUID.randomUUID())
                .stockId(stockId)
                .quotes(Collections.emptyList())
                .build();
        final StockQuote stockQuote2 = StockQuote.builder()
                .id(UUID.randomUUID())
                .stockId(stockId)
                .quotes(Collections.emptyList())
                .build();
        List<StockQuote> stockQuotes = Arrays.asList(stockQuote1, stockQuote2);

        when(stockRepository.findByStockId(stockId)).thenReturn(stockQuotes);

        List<StockQuoteDto> result = stockQuoteService.getStockQuoteByStockId(stockId);

        assertNotNull(result);
        assertEquals(2, result.size());

        result.forEach(stockQuoteDto -> assertEquals(stockId, stockQuoteDto.stockId()));

        result.forEach(stockQuoteDto -> {
            assertNotNull(stockQuoteDto.id());
            assertNotNull(stockQuoteDto.quotes());
        });
    }

    @Test
    void testGetAllStockQuote_ReturnsStockQuoteDtoList() {
        final StockQuote stockQuote1 = StockQuote.builder()
                .id(UUID.randomUUID())
                .stockId("petr4")
                .quotes(Collections.emptyList())
                .build();
        final StockQuote stockQuote2 = StockQuote.builder()
                .id(UUID.randomUUID())
                .stockId("aapl34")
                .quotes(Collections.emptyList())
                .build();
        List<StockQuote> stockQuotes = Arrays.asList(stockQuote1, stockQuote2);

        when(stockRepository.findAll()).thenReturn(stockQuotes);

        List<StockQuoteDto> result = stockQuoteService.getAllStockQuote();

        assertNotNull(result);
        assertEquals(2, result.size());

        StockQuoteDto stockQuoteDto1 = result.get(0);
        assertEquals(stockQuote1.getId(), stockQuoteDto1.id());
        assertEquals(stockQuote1.getStockId(), stockQuoteDto1.stockId());
        assertEquals(stockQuote1.getQuotes().size(), stockQuoteDto1.quotes().size());

        StockQuoteDto stockQuoteDto2 = result.get(1);
        assertEquals(stockQuote2.getId(), stockQuoteDto2.id());
        assertEquals(stockQuote2.getStockId(), stockQuoteDto2.stockId());
        assertEquals(stockQuote2.getQuotes().size(), stockQuoteDto2.quotes().size());
    }

}

