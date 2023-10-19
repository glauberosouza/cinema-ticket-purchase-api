package com.glauber.cinema.Ticket.controller;

import com.glauber.cinema.Ticket.controller.request.PurchaseRequest;
import com.glauber.cinema.Ticket.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Void>savePurchase(@RequestBody PurchaseRequest purchaseRequest){
        //Todo: converter um request para entidade
        //Todo: converter a entidade para um response
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
