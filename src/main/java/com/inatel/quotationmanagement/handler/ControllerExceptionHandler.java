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
        return new Error("Stock não encontrada para registro", HttpStatus.NOT_FOUND, stockNotFoundException.getMessage());
    }

    @ExceptionHandler(StockManagerConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public Error stockManagerConnectionException(StockManagerConnectionException stockManagerConnectionException){
        return new Error("Conexão Stock Manager Error", HttpStatus.NOT_FOUND, stockManagerConnectionException.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error invalidFormatException(InvalidFormatException invalidFormatException){
        return new Error("Formato Invalido", HttpStatus.NOT_FOUND, invalidFormatException.getMessage());
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error jsonMappingException(JsonMappingException jsonMappingException){
        return new Error("Exceção Mapeamento Json", HttpStatus.NOT_FOUND, jsonMappingException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        return new Error("Método Argumento não é válido", HttpStatus.NOT_FOUND, methodArgumentNotValidException.getMessage());
    }

    @ExceptionHandler(JDBCConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public Error jDBCConnectionException(JDBCConnectionException jDBCConnectionException){
        return new Error("Exceção de Conexão do JDBC", HttpStatus.NOT_FOUND, jDBCConnectionException.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Error nullPointerException(NullPointerException nullPointerException){
        return new Error("Exceção Null Pointer", HttpStatus.NOT_FOUND, nullPointerException.getMessage());
    }

}
