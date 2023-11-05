package com.glauber.cinema.Ticket.service.impl;

import com.glauber.cinema.Ticket.controller.request.PurchaseUpdateRequest;
import com.glauber.cinema.Ticket.domain.model.Purchase;
import com.glauber.cinema.Ticket.domain.model.Ticket;
import com.glauber.cinema.Ticket.domain.repository.PurchaseRepository;
import com.glauber.cinema.Ticket.domain.repository.RoomRepository;
import com.glauber.cinema.Ticket.domain.repository.TicketRepository;
import com.glauber.cinema.Ticket.exception.OccupiedChairException;
import com.glauber.cinema.Ticket.exception.PurchaseNotFoundException;
import com.glauber.cinema.Ticket.service.PurchaseService;
import com.glauber.cinema.Ticket.service.RoomService;
import com.glauber.cinema.Ticket.service.TicketService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

//TODO: Quando for alterado o preço no ticket deve ser replicado no purchase
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;
    private final PurchaseRepository purchaseRepository;
    private final TicketService ticketService;

    @Autowired
    public PurchaseServiceImpl(RoomService roomService, RoomRepository roomRepository, TicketRepository ticketRepository, PurchaseRepository purchaseRepository, TicketService ticketService) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.ticketRepository = ticketRepository;
        this.purchaseRepository = purchaseRepository;
        this.ticketService = ticketService;
    }

    @Transactional
    @Override
    public void save(Purchase purchase) {
        var chair = roomService.getChair(purchase);
        if (!chair.getEmpty()) {
            throw new OccupiedChairException("Poltrona já ocupada");
        }
        chair.setEmpty(false);
        var room = roomService.findRoom(purchase.getRoomNumber().longValue());
        var ticket = Ticket.of(purchase, room);
        ticket.setCreateAt(LocalDate.now());
        ticketService.save(ticket);
        purchase.setTickets(List.of(ticket));
        purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase update(Long id, PurchaseUpdateRequest purchaseUpdateRequest) {
        var purchaseToUpdate = purchaseRepository.findById(id);
        if (purchaseToUpdate.isEmpty()) {
            throw new PurchaseNotFoundException("A compra com o id: " + id + " informado não foi localizado");

        }
        var purchase = Purchase.updateValues(purchaseUpdateRequest, purchaseToUpdate.get());
        return purchaseRepository.save(purchase);
    }

    @Override
    public void deleteById(Long id) {
        var purchaseById = purchaseRepository.findById(id);
        if (purchaseById.isEmpty()) {
            throw new PurchaseNotFoundException("A compra com o id: " + id + " informado não foi localizado");
        }
        Purchase purchase = purchaseById.get();
        purchase.getChair().setEmpty(true);

        purchaseRepository.delete(purchase);
    }
}
