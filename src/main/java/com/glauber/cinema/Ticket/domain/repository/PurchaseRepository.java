package com.glauber.cinema.Ticket.domain.repository;

import com.glauber.cinema.Ticket.domain.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
