package com.inatel.quotationmanagement.service.validation;

import com.inatel.quotationmanagement.model.entities.StockQuote;

public interface StockValidator {
    void isValid(StockQuote stockQuote);
}
