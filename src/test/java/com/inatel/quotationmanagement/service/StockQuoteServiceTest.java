package com.inatel.quotationmanagement.service;

import com.inatel.quotationmanagement.adapter.StockAdapter;
import com.inatel.quotationmanagement.mapper.StockMapper;
import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.Quote;
import com.inatel.quotationmanagement.model.entities.StockQuote;
import com.inatel.quotationmanagement.repository.StockRepository;
import com.inatel.quotationmanagement.service.validation.StockValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class StockQuoteServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private List<StockValidator> stockValidator;

    @InjectMocks
    private StockQuoteService stockQuoteService;

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
    void givenValidStockId_whenGetStockQuoteByStockId_thenReturnStockQuoteDtoList() {

        List<StockQuote> stockQuoteList = new ArrayList<>();
        String stockId = "petr4";

        StockQuote stockQuote1 = createStockQuote(stockId, LocalDate.of(2023, 5, 9), BigDecimal.valueOf(23.0));
        stockQuoteList.add(stockQuote1);

        StockQuote stockQuote2 = createStockQuote(stockId, LocalDate.of(2023, 5, 9), BigDecimal.valueOf(23.0));
        stockQuoteList.add(stockQuote2);

        when(stockRepository.findByStockId(stockId)).thenReturn(stockQuoteList);

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
    void givenValidStock_whenSavingStockQuote_thenReturnStockQuoteDto() {

        StockQuoteDto stockQuoteDto = createStockQuoteDto("petr4", LocalDate.now(), BigDecimal.valueOf(20L));

        StockQuote stockQuote = createStockQuote("petr4", LocalDate.now(), BigDecimal.valueOf(20L));

        try (MockedStatic<StockMapper> stockMapperMock = Mockito.mockStatic(StockMapper.class)) {
            stockMapperMock.when(() -> StockMapper.toStockQuote(stockQuoteDto)).thenReturn(stockQuote);
            stockMapperMock.when(() -> StockMapper.toStockQuoteDto(stockQuote)).thenReturn(stockQuoteDto);

            when(stockRepository.save(any(StockQuote.class))).thenReturn(stockQuote);

            StockQuoteDto result = stockQuoteService.saveStockQuote(stockQuoteDto);

            assertNotNull(result);
            assertEquals(stockQuoteDto.id(), result.id());
            assertEquals(stockQuoteDto.stockId(), result.stockId());
            assertEquals(stockQuoteDto.quotes().size(), result.quotes().size());
            assertTrue(result.quotes().containsKey(LocalDate.now()));
            assertEquals(BigDecimal.valueOf(20L), result.quotes().get(LocalDate.now()));
        }
    }

    @Test
    void givenValidStockQuote_whenGetAllStockQuote_thenReturnsStockQuoteDtoList() {

        List<StockQuote> stockQuoteList = new ArrayList<>();

        StockQuote stockQuote1 = createStockQuote("petr4", LocalDate.of(2023, 5, 9), BigDecimal.valueOf(23.0));
        stockQuoteList.add(stockQuote1);

        StockQuote stockQuote2 = createStockQuote("aapl34", LocalDate.of(2023, 5, 9), BigDecimal.valueOf(41.0));
        stockQuoteList.add(stockQuote2);

        when(stockRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(stockQuoteList));

        Page<StockQuoteDto> result = stockQuoteService.getAllStockQuote(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());

        StockQuoteDto stockQuoteDto1 = result.getContent().get(0);
        assertEquals(stockQuote1.getId(), stockQuoteDto1.id());
        assertEquals(stockQuote1.getStockId(), stockQuoteDto1.stockId());
        assertEquals(stockQuote1.getQuotes().size(), stockQuoteDto1.quotes().size());

        StockQuoteDto stockQuoteDto2 = result.getContent().get(1);
        assertEquals(stockQuote2.getId(), stockQuoteDto2.id());
        assertEquals(stockQuote2.getStockId(), stockQuoteDto2.stockId());
        assertEquals(stockQuote2.getQuotes().size(), stockQuoteDto2.quotes().size());
    }

}