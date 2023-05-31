package com.inatel.quotationmanagement.service.validation;

import com.inatel.quotationmanagement.adapter.StockAdapter;
import com.inatel.quotationmanagement.exception.StockNotFoundException;
import com.inatel.quotationmanagement.model.entities.StockQuote;
import org.springframework.stereotype.Component;

@Component
public class DefaultStockValidator implements StockValidator {

    private final StockAdapter stockAdapter;

    public DefaultStockValidator(StockAdapter stockAdapter) {
        this.stockAdapter = stockAdapter;
    }

    @Override
    public void isValid(StockQuote stockQuote) {
        if (stockAdapter.getAllStock().stream()
                .noneMatch(stock -> stock.id().equals(stockQuote.getStockId()))) {
            throw new StockNotFoundException(stockQuote);
        }

    }

}