package com.glauber.cinema.Ticket.controller.converter;

import com.glauber.cinema.Ticket.controller.request.PurchaseRequest;
import com.glauber.cinema.Ticket.controller.request.PurchaseUpdateRequest;
import com.glauber.cinema.Ticket.controller.response.PurchaseResponse;
import com.glauber.cinema.Ticket.controller.response.PurchaseUpdatedResponse;
import com.glauber.cinema.Ticket.domain.model.Purchase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseConverter {
    public Purchase toPurchaseEntity(PurchaseRequest request) {
        var purchase = new Purchase();
        purchase.setRoomNumber(request.getRoomNumber());
        purchase.setChairLine(request.getChairLine());
        purchase.setChairNumber(request.getChairNumber());
        purchase.setDate(request.getDate());
        purchase.setSession(request.getSession());
        purchase.setPrice(request.getPrice());
        purchase.setQuantity(request.getQuantity());
        return purchase;
    }

    public Purchase toPurchaseEntity(PurchaseUpdateRequest purchaseUpdateRequest) {
        var purchase = new Purchase();
        purchase.setPrice(purchaseUpdateRequest.getPrice());
        purchase.setQuantity(purchaseUpdateRequest.getQuantity());
        return purchase;
    }

    public PurchaseResponse toPurchaseResponse(Purchase purchase) {
        var purchaseResponse = new PurchaseResponse();
        purchaseResponse.setId(purchase.getId());
        purchaseResponse.setPrice(purchase.getPrice());
        purchaseResponse.setQuantity(purchase.getQuantity());
        return purchaseResponse;
    }

    public PurchaseUpdatedResponse toPurchaseResponseUpdated(Purchase purchase) {
        PurchaseUpdatedResponse purchaseUpdatedResponse = new PurchaseUpdatedResponse();
        purchaseUpdatedResponse.setPrice(purchase.getPrice());
        purchaseUpdatedResponse.setQuantity(purchase.getQuantity());
        return purchaseUpdatedResponse;
    }
    public Purchase purchaseUpdateEntityToPurchaseEntity(PurchaseUpdateRequest purchaseUpdateRequest){
        Purchase purchaseUpdated = new Purchase();
        purchaseUpdated.setPrice(purchaseUpdateRequest.getPrice());
        purchaseUpdated.setQuantity(purchaseUpdateRequest.getQuantity());
        return purchaseUpdated;
    }

    public List<PurchaseResponse> toListOfPurchaseResponse(List<Purchase> purchases) {
        List<PurchaseResponse> purchaseResponses = new ArrayList<>();
        for (var purchase : purchases) {
            purchaseResponses.add(toPurchaseResponse(purchase));
        }
        return purchaseResponses;
    }
}


