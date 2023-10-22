package com.glauber.cinema.Ticket.templates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    private Integer roomNumber;
    private String chairLine;
    private int chairNumber;
    private String date;
    private String session;
    private String price;
    private int quantity;

    /*public static com.glauber.cinema.Ticket.controller.request.PurchaseRequest from(PurchaseRequest purchaseRequest) {
        com.glauber.cinema.Ticket.controller.request.PurchaseRequest purchaseConverted = new com.glauber.cinema.Ticket.controller.request.PurchaseRequest();
        purchaseConverted.setRoomNumber(purchaseRequest.getRoomNumber());
        purchaseConverted.setChairLine(purchaseRequest.getChairLine());
        purchaseConverted.setChairNumber(purchaseRequest.getChairNumber());
        purchaseConverted.setDate(LocalDate.parse(purchaseRequest.getDate().toString()));
        purchaseConverted.setSession(LocalTime.parse(purchaseRequest.getSession().toString()));
        purchaseConverted.setPrice(purchaseRequest.getPrice());
        purchaseConverted.setQuantity(purchaseRequest.getQuantity());
        return purchaseConverted;

    }*/


}
