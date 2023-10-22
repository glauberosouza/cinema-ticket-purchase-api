package com.glauber.cinema.Ticket.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class PurchaseUpdatedResponse {
    private BigDecimal price;
    private Integer quantity;
}
