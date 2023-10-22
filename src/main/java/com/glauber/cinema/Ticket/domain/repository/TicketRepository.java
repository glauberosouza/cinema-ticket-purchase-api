package com.glauber.cinema.Ticket.domain.repository;

import com.glauber.cinema.Ticket.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
