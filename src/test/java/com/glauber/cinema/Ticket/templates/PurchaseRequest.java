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
}
