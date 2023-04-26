package com.inatel.quotationmanagement.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockQuoteDto{
    private UUID id;

    @NotNull
    private String stockId;

    @NotNull
    private Map<LocalDate, BigDecimal> quotes = new HashMap<>();;
}



