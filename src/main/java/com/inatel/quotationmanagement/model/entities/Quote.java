package com.inatel.quotationmanagement.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Quote {

    @Id
    private String id;
    @ManyToOne
    private Stock stock;
    private LocalDate date;
    private BigDecimal price;

}
