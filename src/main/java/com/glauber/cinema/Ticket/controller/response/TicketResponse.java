package com.glauber.cinema.Ticket.controller.response;

import com.glauber.cinema.Ticket.domain.model.Chair;
import com.glauber.cinema.Ticket.domain.model.Room;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class TicketResponse {
    private Long id;

    private LocalDate createAt;

    private LocalDate ticketDay;

    private String number;

    private BigDecimal price;

    private LocalTime session;

    private Chair chair;

    private Long purchaseId;

    private Room room;
}