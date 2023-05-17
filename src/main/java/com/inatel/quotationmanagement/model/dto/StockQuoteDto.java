package com.inatel.quotationmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Builder
public record StockQuoteDto(
        UUID id,
        @NotBlank(message = "stockId must not be blank")
        String stockId,
        @NotEmpty(message = "quotes must not be empty")
        Map<
                @NotNull(message = "date must not be null")
                        LocalDate,
                @Positive(message = "quote value must be positive")
                        BigDecimal
                > quotes
) {
    public StockQuoteDto {
        Objects.requireNonNull(stockId, "stockId cannot be null");
        Objects.requireNonNull(quotes, "quotes cannot be null");
    }
}



