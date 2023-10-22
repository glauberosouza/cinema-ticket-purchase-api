package com.glauber.cinema.Ticket.exception;

public class ChairNotFoundException extends RuntimeException{
    public ChairNotFoundException() {
        super("A poltrona especificada não foi localizada no sistema.");
    }
}
