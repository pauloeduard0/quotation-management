package com.inatel.quotationmanagement.mapper;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.Quote;
import com.inatel.quotationmanagement.model.entities.StockQuote;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StockMapper {
    public static List<StockQuote> toStockQuoteList(List<StockQuoteDto> stockQuoteDTOList) {
        return stockQuoteDTOList.stream().map(StockMapper::toStockQuote).collect(Collectors.toList());
    }

    public static List<StockQuoteDto> toStockQuoteDtoList(List<StockQuote> stockQuoteList) {
        return stockQuoteList.stream().map(StockMapper::toStockQuoteDto).collect(Collectors.toList());
    }

    public static StockQuote toStockQuote(StockQuoteDto stockQuoteDto) {
        UUID id = stockQuoteDto.id();
        String stockId = stockQuoteDto.stockId();

        StockQuote stockQuote = StockQuote.builder()
                .id(id)
                .stockId(stockId)
                .quotes(new ArrayList<>())
                .build();

        stockQuoteDto.quotes().entrySet().stream().forEach(quoteEntry -> stockQuote.addQuote(Quote.builder()
                .date(quoteEntry.getKey())
                .value(quoteEntry.getValue())
                .build()));

        return stockQuote;
    }

    public static StockQuoteDto toStockQuoteDto(StockQuote stockQuote) {
        Map<LocalDate, BigDecimal> quotes = stockQuote.getQuotes().stream()
                .collect(Collectors.toMap(Quote::getDate, Quote::getValue));

        return new StockQuoteDto(
                stockQuote.getId(),
                stockQuote.getStockId(),
                quotes
        );
    }

}