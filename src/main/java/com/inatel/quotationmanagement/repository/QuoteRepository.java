package com.inatel.quotationmanagement.repository;

import com.inatel.quotationmanagement.model.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {
}