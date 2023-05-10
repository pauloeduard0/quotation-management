package com.inatel.quotationmanagement.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.inatel.quotationmanagement.exception.StockManagerConnectionException;
import com.inatel.quotationmanagement.exception.StockNotFoundException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StockNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ProblemDetail handleStockNotFoundException(StockNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Stock Not Found for Registration");
        problemDetail.setType(URI.create("https://api.quotationmanagement.com/errors/not-found"));
        return problemDetail;
    }

    @ExceptionHandler(StockManagerConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ProblemDetail handleStockManagerConnectionException(StockManagerConnectionException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        problemDetail.setTitle("Stock Manager Connection Exception");
        problemDetail.setType(URI.create("https://api.quotationmanagement.com/errors/connection-exception"));
        return problemDetail;
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ProblemDetail handleInvalidFormatException(InvalidFormatException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Invalid Format Exception");
        problemDetail.setType(URI.create("https://api.quotationmanagement.com/errors/invalid-format-exception"));
        return problemDetail;
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ProblemDetail handleJsonMappingException(JsonMappingException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Json Mapping Exception");
        problemDetail.setType(URI.create("https://api.bookmarks.com/errors/json-mapping-exception"));
        return problemDetail;
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ProblemDetail handleWebExchangeBindException(WebExchangeBindException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Web Exchange Bind Exception");
        problemDetail.setType(URI.create("https://api.bookmarks.com/errors/web-exchange-bind-exception"));
        return problemDetail;
    }

    @ExceptionHandler(JDBCConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ProblemDetail handleJDBCConnectionException(JDBCConnectionException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        problemDetail.setTitle("JDBC Connection Exception");
        problemDetail.setType(URI.create("https://api.bookmarks.com/errors/jdbc-connection-exception"));
        return problemDetail;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleNullPointerException(NullPointerException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Null Pointer Exception");
        problemDetail.setType(URI.create("https://api.bookmarks.com/errors/null-pointer-exception"));
        return problemDetail;
    }


}
