package com.inatel.quotationmanagement.controller;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import com.inatel.quotationmanagement.model.entities.Quote;
import com.inatel.quotationmanagement.model.entities.Stock;
import com.inatel.quotationmanagement.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockRepository repository;
    @PostMapping
    public void cadastrar(@RequestBody StockQuoteDto stockQuoteDto){
        repository.save(new Stock(stockQuoteDto));
    }
}
