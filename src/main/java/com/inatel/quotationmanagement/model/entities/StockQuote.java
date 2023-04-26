package com.inatel.quotationmanagement.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StockQuote {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String stockId;

    @OneToMany(mappedBy = "stockQuote", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Quote> quotes = new ArrayList<>();

    public void addQuote(Quote quote) {
        quotes.add(quote);
        quote.setStockQuote(this);
    }
}
