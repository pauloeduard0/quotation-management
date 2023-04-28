package com.inatel.quotationmanagement.repository;

import com.inatel.quotationmanagement.model.entities.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface StockRepository extends JpaRepository<StockQuote, UUID> {
    List<StockQuote> findByStockId(String stockId);
}
