package com.inatel.quotationmanagement.exception;

import com.inatel.quotationmanagement.model.entities.StockQuote;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(StockQuote stockQuote) {
        super(String.format("Stock with stockId='%s' was not found.", stockQuote.getStockId()));
    }
}
