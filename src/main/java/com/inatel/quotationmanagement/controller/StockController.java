package com.inatel.quotationmanagement.controller;

import com.inatel.quotationmanagement.adapter.StockAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stockcache")
@Slf4j
public class StockController {

    private final StockAdapter stockAdapter;

    public StockController(StockAdapter stockAdapter) {
        this.stockAdapter = stockAdapter;
    }

    @DeleteMapping
    public void deleteStockCache() {
        stockAdapter.clearStockCache();
    }

}