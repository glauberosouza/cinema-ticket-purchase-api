package com.glauber.cinema.Ticket.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    @Min(value = 1, message = "O número da sala deve ser no mínimo 1.")
    private Integer roomNumber;
    @NotBlank(message = "A fileira não pode estar em branco.")
    private String chairLine;
    @Min(value = 1, message = "O número da poltrona deve ser no mínimo 1.")
    private Integer chairNumber;
    @NotNull(message = "A data não pode ser nula.")
    private LocalDate date;
    @NotNull(message = "A hora da sessão não pode ser nula.")
    private LocalTime session;
    @Min(value = 1, message = "O preço deve ser no mínimo 1.")
    private BigDecimal price;
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    private Integer quantity;
}
