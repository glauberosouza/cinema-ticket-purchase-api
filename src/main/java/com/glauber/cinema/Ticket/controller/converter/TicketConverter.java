package com.glauber.cinema.Ticket.controller.converter;

import com.glauber.cinema.Ticket.controller.response.TicketResponse;
import com.glauber.cinema.Ticket.domain.model.Ticket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class TicketConverter {
    public TicketResponse toTicketResponse(Ticket ticket) {

        var response = new TicketResponse();
        response.setId(ticket.getId());
        response.setCreateAt(ticket.getCreateAt());
        response.setTicketDay(ticket.getTicketDay());
        response.setNumber(ticket.getNumber());
        response.setPrice(ticket.getPrice());
        response.setSession(ticket.getSession());
        response.setChair(ticket.getChair());
        response.setPurchaseId(ticket.getPurchase().getId());
        response.setRoom(ticket.getRoom());
        return response;
    }

    public List<TicketResponse> toTicketResponse(List<Ticket> tickets) {
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for (var ticket : tickets) {
            ticketResponses.add(toTicketResponse(ticket));
        }
        return ticketResponses;
    }

    public Ticket toTicketEntity(TicketResponse ticketResponse) {
        var ticket = new Ticket();
        ticket.setId(ticketResponse.getId());
        ticket.setCreateAt(ticketResponse.getCreateAt());
        ticket.setTicketDay(ticketResponse.getTicketDay());
        ticket.setNumber(ticketResponse.getNumber());
        ticket.setPrice(ticketResponse.getPrice());
        ticket.setSession(ticketResponse.getSession());
        ticket.setChair(ticketResponse.getChair());
        ticket.getPurchase().setId(ticketResponse.getPurchaseId());
        ticket.setRoom(ticketResponse.getRoom());
        return ticket;
    }

    public List<Ticket> toTicketEntity(List<TicketResponse> ticketResponses) {
        List<Ticket> listTickets = new ArrayList<>();
        for (var ticket : ticketResponses) {
            listTickets.add(toTicketEntity(ticket));
        }
        return listTickets;
    }
}