package com.glauber.cinema.Ticket.exception;

public class PurchaseNotFound extends RuntimeException {
    public PurchaseNotFound(String message) {
        super(message);
    }
}
