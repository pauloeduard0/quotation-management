package com.inatel.quotationmanagement.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.*;

@Table(name = "quote")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private StockQuote stockQuote;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate date;

    private BigDecimal value;

}