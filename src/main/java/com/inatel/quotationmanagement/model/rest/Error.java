package com.inatel.quotationmanagement.model.rest;

import org.springframework.http.HttpStatus;

public record Error(String disclaimer, HttpStatus httpStatusCode, String message) {}
