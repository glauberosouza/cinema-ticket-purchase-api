package com.glauber.cinema.Ticket.templates;


import java.time.LocalDate;

public class PurchaseRequestTemplate {
    public static PurchaseRequest creation() {
        return new PurchaseRequest(
                1, "A", 1, LocalDate.now().toString(), "19:00",
                "20.00", 1
        );
    }
}
