package com.inatel.quotationmanagement.repository;

import com.inatel.quotationmanagement.model.entities.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StockRepository extends JpaRepository<StockQuote, String> {
    List<StockQuote> findByStockId(String id);
}
