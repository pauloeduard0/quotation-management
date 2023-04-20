package com.inatel.quotationmanagement.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record StockQuoteDto(String id, String stockId, Map<LocalDate, BigDecimal> quotes) {

}

