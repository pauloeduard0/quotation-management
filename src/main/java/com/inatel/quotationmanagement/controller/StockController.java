package com.inatel.quotationmanagement.controller;

import com.inatel.quotationmanagement.adapter.StockAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stockcache")
@Slf4j
public class StockController {

    @Autowired
    private StockAdapter stockAdapter;

    @DeleteMapping
    public void deleteStockCache() {
        stockAdapter.clearStockCache();
    }

}
