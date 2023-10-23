package com.glauber.cinema.Ticket.controller;

import com.glauber.cinema.Ticket.controller.converter.PurchaseConverter;
import com.glauber.cinema.Ticket.controller.request.PurchaseRequest;
import com.glauber.cinema.Ticket.controller.request.PurchaseUpdateRequest;
import com.glauber.cinema.Ticket.controller.response.PurchaseResponse;
import com.glauber.cinema.Ticket.controller.response.PurchaseUpdatedResponse;
import com.glauber.cinema.Ticket.domain.model.Purchase;
import com.glauber.cinema.Ticket.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private PurchaseService purchaseService;
    private PurchaseConverter purchaseConverter;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, PurchaseConverter purchaseConverter) {
        this.purchaseService = purchaseService;
        this.purchaseConverter = purchaseConverter;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> savePurchase(@RequestBody @Valid PurchaseRequest purchaseRequest) {
        var purchaseEntity = purchaseConverter.toPurchaseEntity(purchaseRequest);
        purchaseService.save(purchaseEntity);
        var purchaseResponse = purchaseConverter.toPurchaseResponse(purchaseEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseResponse);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> findAll() {
        var allPurchases = purchaseService.findAllPurchases();
        var listOfPurchaseResponse = purchaseConverter.toListOfPurchaseResponse(allPurchases);
        return ResponseEntity.status(HttpStatus.OK).body(listOfPurchaseResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseUpdatedResponse> update(@PathVariable Long id, @RequestBody PurchaseUpdateRequest purchaseUpdateRequest) {
        Purchase purchaseUpdated = purchaseService.update(id, purchaseUpdateRequest);
        PurchaseUpdatedResponse purchaseResponseUpdated = purchaseConverter.toPurchaseResponseUpdated(purchaseUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(purchaseResponseUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
