package com.inatel.quotationmanagement.service;

import com.inatel.quotationmanagement.adapter.StockAdapter;
import com.inatel.quotationmanagement.exception.StockNotFoundException;
import com.inatel.quotationmanagement.mapper.StockMapper;
import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.StockQuote;
import com.inatel.quotationmanagement.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockQuoteService {

    @Autowired
    private StockAdapter stockAdapter;

    @Autowired
    private StockRepository stockRepository;

    public StockQuoteDto saveStockQuote(StockQuoteDto stockQuoteDto) {

        StockQuote stockQuote = StockMapper.toStockQuote(stockQuoteDto);

        if (isStockValid(stockQuote)) {
            return StockMapper.toStockQuoteDto(stockRepository.save(stockQuote));
        }
        throw new StockNotFoundException(stockQuote);
    }

    public List<StockQuoteDto> getStockQuoteByStockId(String stockId) {
        return StockMapper.toStockQuoteDtoList(stockRepository.findByStockId(stockId));
    }

    public Page<StockQuoteDto> getAllStockQuote(Pageable pageable) {
        return stockRepository.findAll(pageable).map(StockMapper::toStockQuoteDto);
    }

    private boolean isStockValid(StockQuote stockQuote) {
        return stockAdapter.getAllStock().stream().anyMatch(stock -> stock.id().equals(stockQuote.getStockId()));
    }

}