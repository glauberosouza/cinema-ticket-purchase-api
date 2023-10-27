package com.glauber.cinema.Ticket.controller.response;

import com.glauber.cinema.Ticket.domain.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    private Long id;
    private int roomNumber;
    private String chairLine;
    private int chairNumber;
    private LocalDate date;
    private LocalTime session;
    private BigDecimal price;
    private Integer quantity;
}
