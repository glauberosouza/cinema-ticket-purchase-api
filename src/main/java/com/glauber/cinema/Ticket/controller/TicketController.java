package com.glauber.cinema.Ticket.controller;

import com.glauber.cinema.Ticket.controller.request.TicketUpdateRequest;
import com.glauber.cinema.Ticket.controller.response.TicketResponse;
import com.glauber.cinema.Ticket.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> findAll() {
        var allResponseTickets = ticketService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allResponseTickets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TicketUpdateRequest ticketUpdateRequest) {
        ticketService.update(id, ticketUpdateRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        ticketService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
