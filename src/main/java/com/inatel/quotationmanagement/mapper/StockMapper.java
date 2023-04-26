package com.inatel.quotationmanagement.mapper;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.Quote;
import com.inatel.quotationmanagement.model.entities.StockQuote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class StockMapper {
    public static List<StockQuote> toStockQuoteList(List<StockQuoteDto> stockQuoteDTOList) {
        return stockQuoteDTOList.stream().map(stockQuoteDto -> toStockQuote(stockQuoteDto)).collect(Collectors.toList());
    }

    public static List<StockQuoteDto> toStockQuoteDtoList(List<StockQuote> stockQuoteList) {
        return stockQuoteList.stream().map(stockQuote -> toStockQuoteDto(stockQuote)).collect(Collectors.toList());
    }

    public static StockQuote toStockQuote(StockQuoteDto stockQuoteDto) {
        StockQuote stockQuote = StockQuote.builder()
                .id(stockQuoteDto.getId())
                .stockId(stockQuoteDto.getStockId())
                .quotes(new ArrayList<>())
                .build();

        stockQuoteDto.getQuotes().entrySet().stream().forEach(quoteEntry -> stockQuote.addQuote(Quote.builder()
                .date(quoteEntry.getKey())
                .value(quoteEntry.getValue())
                .build()));

        return stockQuote;
    }

    public static StockQuoteDto toStockQuoteDto(StockQuote stockQuote) {
        StockQuoteDto stockQuoteDto = StockQuoteDto.builder()
                .id(stockQuote.getId())
                .stockId(stockQuote.getStockId())
                .quotes(new HashMap<>())
                .build();

        stockQuoteDto.setQuotes(stockQuote.getQuotes().stream()
                .collect(Collectors.toMap(Quote::getDate, Quote::getValue)));

        return stockQuoteDto;
    }

}

