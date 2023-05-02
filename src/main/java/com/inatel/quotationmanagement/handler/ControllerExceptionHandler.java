package com.inatel.quotationmanagement.handler;

import com.inatel.quotationmanagement.exception.StockNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.inatel.quotationmanagement.model.rest.Error;
@ControllerAdvice
@ResponseBody
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(StockNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error stockNotFoundException(StockNotFoundException stockNotFoundException){
        return Error.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND)
                .message(stockNotFoundException.getMessage())
                .build();
    }
}
