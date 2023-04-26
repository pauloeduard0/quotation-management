package com.inatel.quotationmanagement.controller;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.service.StockQuoteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@Slf4j
public class StockQuoteController {

    @Autowired
    private StockQuoteService stockQuoteService;

    @GetMapping
    public ResponseEntity<List<StockQuoteDto>> getQuotes(@RequestParam(required = false)Optional<String> stockId)
    {
        List<StockQuoteDto> stockQuoteList;
        if(stockId.isPresent())
        {
            stockQuoteList = stockQuoteService.getStockQuoteByStockId(stockId.get());
        }
        else
        {
            stockQuoteList = stockQuoteService.getAllStockQuote();
        }
        return ResponseEntity.ok(stockQuoteList);
    }

    @PostMapping
    public ResponseEntity<StockQuoteDto> saveQuote(@Valid @RequestBody StockQuoteDto stockQuoteDto)
    {
        return ResponseEntity.created(null).body(stockQuoteService.saveStockQuote(stockQuoteDto));
    }

}
