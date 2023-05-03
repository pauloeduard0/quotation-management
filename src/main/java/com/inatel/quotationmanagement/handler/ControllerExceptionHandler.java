package com.inatel.quotationmanagement.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.inatel.quotationmanagement.exception.StockManagerConnectionException;
import com.inatel.quotationmanagement.exception.StockNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(StockManagerConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public Error stockManagerConnectionException(StockManagerConnectionException stockManagerConnectionException){
        return Error.builder()
                .httpStatusCode(HttpStatus.SERVICE_UNAVAILABLE)
                .message(stockManagerConnectionException.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error invalidFormatExcepetion(InvalidFormatException invalidFormatException){
        return Error.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(invalidFormatException.getMessage())
                .build();
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error jsonMappingException(JsonMappingException jsonMappingException){
        return Error.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(jsonMappingException.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        return Error.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(methodArgumentNotValidException.getMessage())
                .build();
    }

    @ExceptionHandler(JDBCConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public Error jDBCConnectionException(JDBCConnectionException jDBCConnectionException){
        return Error.builder()
                .httpStatusCode(HttpStatus.SERVICE_UNAVAILABLE)
                .message(jDBCConnectionException.getMessage())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Error nullPointerException(NullPointerException nullPointerException){
        return Error.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(nullPointerException.getMessage())
                .build();
    }
}
