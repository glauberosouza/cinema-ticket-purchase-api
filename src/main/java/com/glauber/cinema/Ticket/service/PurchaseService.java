package com.glauber.cinema.Ticket.service;

import com.glauber.cinema.Ticket.controller.request.PurchaseUpdateRequest;
import com.glauber.cinema.Ticket.domain.model.Purchase;

import java.util.List;

public interface PurchaseService {
    void save(Purchase purchase);
    List<Purchase> findAllPurchases();
    Purchase update(Long id, PurchaseUpdateRequest purchaseUpdateRequest);
    void deleteById(Long id);
}
