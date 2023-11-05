package com.glauber.cinema.Ticket.service.impl;

import com.glauber.cinema.Ticket.controller.converter.TicketConverter;
import com.glauber.cinema.Ticket.controller.request.TicketUpdateRequest;
import com.glauber.cinema.Ticket.controller.response.TicketResponse;
import com.glauber.cinema.Ticket.domain.model.Ticket;
import com.glauber.cinema.Ticket.domain.repository.TicketRepository;
import com.glauber.cinema.Ticket.exception.TicketNotFoundException;
import com.glauber.cinema.Ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private TicketConverter converter;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TicketConverter ticketConverter) {
        this.ticketRepository = ticketRepository;
        this.converter = ticketConverter;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<TicketResponse> findAll() {
        var allTickets = ticketRepository.findAll();
        return converter.toTicketResponse(allTickets);
    }

    @Override
    public Ticket update(Long id, TicketUpdateRequest ticket) {
        var ticketById = ticketRepository.findById(id);
        if (!ticketById.isPresent()) {
            throw new TicketNotFoundException("Não foi localizado um ticket com o id: " + id + " inserido");
        }
        var ticketFounded = ticketById.get();
        ticketFounded.setPrice(ticket.getPrice());
        ticketFounded.getPurchase().setPrice(ticket.getPrice());
        ticketRepository.save(ticketFounded);
        return ticketFounded;
    }

    @Override
    public void deleteById(Long id) {
        var ticketById = ticketRepository.findById(id);
        if (!ticketById.isPresent()) {
            throw new TicketNotFoundException("Não foi localizado um ticket com o id: " + id + " inserido");
        }
        var ticket = ticketById.get();
        ticketRepository.delete(ticket);
    }
}
