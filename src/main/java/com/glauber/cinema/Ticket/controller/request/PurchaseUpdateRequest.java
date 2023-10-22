package com.glauber.cinema.Ticket.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseUpdateRequest {
    private BigDecimal price;
    private Integer quantity;
}
