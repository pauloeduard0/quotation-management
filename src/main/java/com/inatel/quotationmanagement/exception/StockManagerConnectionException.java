package com.inatel.quotationmanagement.exception;

public class StockManagerConnectionException extends RuntimeException {

    public StockManagerConnectionException(String stockManagerBaseUrl) {
        super(String.format("It was not possible to communicate with stock-manager at location [%s]", stockManagerBaseUrl));
    }

}