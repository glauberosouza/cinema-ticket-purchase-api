package com.glauber.cinema.Ticket.controller.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateRequest {
    @Min(value = 1, message = "O price deve ser maior ou igual a 1")
    private BigDecimal price;
}