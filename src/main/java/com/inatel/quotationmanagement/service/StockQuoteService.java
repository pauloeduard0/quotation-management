package com.inatel.quotationmanagement.service;


import com.inatel.quotationmanagement.exception.StockNotFoundException;
import com.inatel.quotationmanagement.mapper.StockMapper;
import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.StockQuote;
import com.inatel.quotationmanagement.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class StockQuoteService {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    public StockQuoteDto saveStockQuote(StockQuoteDto stockQuoteDto) {

        StockQuote stockQuote = StockMapper.toStockQuote(stockQuoteDto);

        if (isStockValid(stockQuote)) {
            return StockMapper.toStockQuoteDto(stockRepository.save(stockQuote));
        }
        throw new StockNotFoundException(stockQuote);
    }

    private boolean isStockValid(StockQuote stockQuote) {
        return stockService.getAllStock().stream().anyMatch(stock -> stock.id().equals(stockQuote.getStockId()));
    }

    public List<StockQuoteDto> getStockQuoteByStockId(String stockId) {
        return StockMapper.toStockQuoteDtoList(stockRepository.findByStockId(stockId));
    }

    public List<StockQuoteDto> getAllStockQuote() {
        return StockMapper.toStockQuoteDtoList(stockRepository.findAll());
    }


}


