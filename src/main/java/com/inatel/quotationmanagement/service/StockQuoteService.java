package com.inatel.quotationmanagement.service;

import com.inatel.quotationmanagement.mapper.StockMapper;
import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.StockQuote;
import com.inatel.quotationmanagement.repository.StockRepository;
import com.inatel.quotationmanagement.service.validation.StockValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockQuoteService {

    private final StockRepository stockRepository;

    private final List<StockValidator> listStock;

    public StockQuoteService(StockRepository stockRepository, List<StockValidator> listStock) {
        this.stockRepository = stockRepository;
        this.listStock = listStock;
    }

    public StockQuoteDto saveStockQuote(StockQuoteDto stockQuoteDto) {

        StockQuote stockQuote = StockMapper.toStockQuote(stockQuoteDto);

        listStock.forEach(stockVal -> stockVal.isValid(stockQuote));

        return StockMapper.toStockQuoteDto(stockRepository.save(stockQuote));
    }

    public List<StockQuoteDto> getStockQuoteByStockId(String stockId) {
        return StockMapper.toStockQuoteDtoList(stockRepository.findByStockId(stockId));
    }

    public Page<StockQuoteDto> getAllStockQuote(Pageable pageable) {
        return stockRepository.findAll(pageable).map(StockMapper::toStockQuoteDto);
    }
}