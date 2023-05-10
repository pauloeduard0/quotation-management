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
        UUID id = UUID.randomUUID();
        String stockId = "petr4";
        Map<LocalDate, BigDecimal> quotes1 = new HashMap<>();
        quotes1.put(LocalDate.of(2023, 5, 9), BigDecimal.valueOf(23.0));
        quotes1.put(LocalDate.of(2023, 5, 10), BigDecimal.valueOf(24.0));

        StockQuoteDto stockQuoteDto = new StockQuoteDto(id, stockId, quotes1);

        Stock stock = Stock.builder()
                .id("petr4")
                .description("Stock petr4")
                .build();

        when(stockAdapter.getAllStock()).thenReturn(Arrays.asList(stock));

        when(stockRepository.save(Mockito.any(StockQuote.class))).thenReturn(StockMapper.toStockQuote(stockQuoteDto));

        StockQuoteDto result = stockQuoteService.saveStockQuote(stockQuoteDto);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(stockId, result.stockId());
        assertEquals(quotes1.size(), result.quotes().size());
        assertTrue(result.quotes().containsKey(LocalDate.of(2023, 5, 9)));
        assertEquals(BigDecimal.valueOf(23.0), result.quotes().get(LocalDate.of(2023, 5, 9)));
        assertTrue(result.quotes().containsKey(LocalDate.of(2023, 5, 10)));
        assertEquals(BigDecimal.valueOf(24.0), result.quotes().get(LocalDate.of(2023, 5, 10)));
    }


    @Test
    void testSaveStockQuote_InvalidStock_ThrowsStockNotFoundException() {
        StockQuoteDto stockQuoteDto = StockQuoteDto.builder()
                .id(UUID.randomUUID())
                .stockId("petr4")
                .quotes(Collections.emptyMap())
                .build();
        when(stockAdapter.getAllStock()).thenReturn(Collections.emptyList());

        assertThrows(StockNotFoundException.class, () -> {
            stockQuoteService.saveStockQuote(stockQuoteDto);
        });
    }
}

