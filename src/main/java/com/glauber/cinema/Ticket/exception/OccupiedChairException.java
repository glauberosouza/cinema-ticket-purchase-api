package com.glauber.cinema.Ticket.exception;

public class OccupiedChairException extends RuntimeException {
    public OccupiedChairException(String message) {
        super(message);
    }
}
