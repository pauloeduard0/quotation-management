package com.inatel.quotationmanagement.repository;

import com.inatel.quotationmanagement.model.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findByStockId(String id);
}
