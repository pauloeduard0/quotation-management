package com.inatel.quotationmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record StockQuoteDto(
        @JsonProperty("id") UUID id,
        @JsonProperty("stockId") String stockId,
        @JsonProperty("quotes") Map<LocalDate, BigDecimal> quotes) {

    public StockQuoteDto {
        Objects.requireNonNull(stockId, "stockId cannot be null");
        Objects.requireNonNull(quotes, "quotes cannot be null");
    }
}



