package com.glauber.cinema.Ticket.service.impl;

import com.glauber.cinema.Ticket.controller.request.PurchaseUpdateRequest;
import com.glauber.cinema.Ticket.domain.model.Chair;
import com.glauber.cinema.Ticket.domain.model.Purchase;
import com.glauber.cinema.Ticket.domain.model.Ticket;
import com.glauber.cinema.Ticket.domain.repository.PurchaseRepository;
import com.glauber.cinema.Ticket.domain.repository.RoomRepository;
import com.glauber.cinema.Ticket.domain.repository.TicketRepository;
import com.glauber.cinema.Ticket.exception.OccupiedChairException;
import com.glauber.cinema.Ticket.exception.PurchaseNotFound;
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
        if (!chair.isEmpty()) {
            throw new OccupiedChairException("Poltrona já ocupada");
        }
        var ticket = Ticket.of(purchase, chair.getRoom());
        ticket.setCreateAt(LocalDate.now());
        purchase.setTickets(List.of(ticket));
        purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> findAllPurchases() {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        return purchaseList;
    }

    @Override
    public Purchase update(Long id, PurchaseUpdateRequest purchaseUpdateRequest) {
        Optional<Purchase> purchaseToUpdate = purchaseRepository.findById(id);
        if (purchaseToUpdate.isEmpty()) {
            throw new PurchaseNotFound("A compra com o id: " + id + " informado não foi localizado");

        }
        Purchase purchase = Purchase.updateValues(purchaseUpdateRequest, purchaseToUpdate.get());
        return purchaseRepository.save(purchase);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Purchase> purchaseById = purchaseRepository.findById(id);
        if (purchaseById.isPresent()) {
            Purchase purchase = purchaseById.get();
            purchaseRepository.delete(purchase);
        }
    }
}
