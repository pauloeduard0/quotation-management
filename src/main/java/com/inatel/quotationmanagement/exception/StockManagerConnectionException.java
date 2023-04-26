package com.inatel.quotationmanagement.exception;

public class StockManagerConnectionException extends RuntimeException{

    public StockManagerConnectionException(String stockManagerBaseUrl)
    {
        super(String.format("Was not possible to communicate with stock-manager at location [%s]", stockManagerBaseUrl));
    }

}
