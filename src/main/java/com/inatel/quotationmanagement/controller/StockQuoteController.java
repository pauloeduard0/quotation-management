package com.inatel.quotationmanagement.controller;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.service.StockQuoteService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quote")
@Slf4j
public class StockQuoteController {

    @Autowired
    private StockQuoteService stockQuoteService;

    @GetMapping
    public ResponseEntity<Page<StockQuoteDto>> getAllQuotes(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(stockQuoteService.getAllStockQuote(pageable));
    }

    @GetMapping
    @RequestMapping("/{stockId}")
    public ResponseEntity<Page<StockQuoteDto>> getQuotes(@PathVariable String stockId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(stockQuoteService.getStockQuoteByStockId(stockId, pageable));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<StockQuoteDto> saveQuote(@Valid @RequestBody StockQuoteDto stockQuoteDto) {
        return ResponseEntity.created(null).body(stockQuoteService.saveStockQuote(stockQuoteDto));
    }

}
