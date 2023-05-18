package com.inatel.quotationmanagement.repository;

import com.inatel.quotationmanagement.model.entities.StockQuote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface StockRepository extends JpaRepository<StockQuote, UUID> {
    Page<StockQuote> findByStockId(String stockId, Pageable pageable);
}