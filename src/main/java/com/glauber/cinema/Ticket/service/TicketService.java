package com.glauber.cinema.Ticket.service;

import com.glauber.cinema.Ticket.controller.request.TicketUpdateRequest;
import com.glauber.cinema.Ticket.controller.response.TicketResponse;
import com.glauber.cinema.Ticket.domain.model.Ticket;

import java.util.List;

public interface TicketService {
    List<TicketResponse> findAll();
    Ticket update(Long id, TicketUpdateRequest ticket);

    void deleteById(Long id);
}
