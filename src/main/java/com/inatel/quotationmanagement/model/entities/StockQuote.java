package com.inatel.quotationmanagement.model.entities;

import com.inatel.quotationmanagement.model.dto.StockQuoteDto;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Stock")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stockId;

    @OneToMany(mappedBy = "stock")
    private List<Quote> quotes = new ArrayList<>();

    public void addQuote(Quote quote) {
        quote.add(quote);
        quote.setStockQuote(this);
    }
}
