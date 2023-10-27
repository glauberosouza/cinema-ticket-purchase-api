package com.glauber.cinema.Ticket.service.impl;

import com.glauber.cinema.Ticket.controller.request.PurchaseUpdateRequest;
import com.glauber.cinema.Ticket.domain.model.Chair;
import com.glauber.cinema.Ticket.domain.model.Purchase;
import com.glauber.cinema.Ticket.domain.model.Room;
import com.glauber.cinema.Ticket.domain.model.Ticket;
import com.glauber.cinema.Ticket.domain.repository.PurchaseRepository;
import com.glauber.cinema.Ticket.domain.repository.RoomRepository;
import com.glauber.cinema.Ticket.domain.repository.TicketRepository;
import com.glauber.cinema.Ticket.exception.ChairNotFoundException;
import com.glauber.cinema.Ticket.exception.OccupiedChairException;
import com.glauber.cinema.Ticket.exception.PurchaseNotFoundException;
import com.glauber.cinema.Ticket.service.PurchaseService;
import com.glauber.cinema.Ticket.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseServiceImpl(RoomService roomService, RoomRepository roomRepository, TicketRepository ticketRepository, PurchaseRepository purchaseRepository) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.ticketRepository = ticketRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void save(Purchase purchase) {
        Chair chair = roomService.getChair(purchase);
        if (!chair.getEmpty()) {
            throw new OccupiedChairException("Poltrona já ocupada");
        }
        chair.setEmpty(false);
        var ticket = Ticket.of(purchase, chair.getRoom());
        ticket.setCreateAt(LocalDate.now());
        purchase.setTickets(List.of(ticket));
        purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase update(Long id, PurchaseUpdateRequest purchaseUpdateRequest) {
        Optional<Purchase> purchaseToUpdate = purchaseRepository.findById(id);
        if (purchaseToUpdate.isEmpty()) {
            throw new PurchaseNotFoundException("A compra com o id: " + id + " informado não foi localizado");

        }
        Purchase purchase = Purchase.updateValues(purchaseUpdateRequest, purchaseToUpdate.get());
        return purchaseRepository.save(purchase);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Purchase> purchaseById = purchaseRepository.findById(id);
        if (purchaseById.isEmpty()) {
            throw new PurchaseNotFoundException("A compra com o id: " + id + " informado não foi localizado");
        }
        Purchase purchase = purchaseById.get();
        purchase.getChair().setEmpty(true);

        purchaseRepository.delete(purchase);
    }
}
