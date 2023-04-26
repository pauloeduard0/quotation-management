package com.inatel.quotationmanagement.repository;

import com.inatel.quotationmanagement.model.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
