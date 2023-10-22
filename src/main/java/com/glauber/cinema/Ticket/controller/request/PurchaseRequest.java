package com.glauber.cinema.Ticket.controller.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    @Min(value = 1, message = "Número da sala inválido")
    private Integer roomNumber;
    @NotBlank(message = "Fileira inválida")
    private String chairLine;
    @Min(value = 1, message = "Número da poltrona inválido")
    private Integer chairNumber;
    @NotNull(message = "Dia inválido")
    private LocalDate date;
    @NotNull(message = "Sessão inválida")
    private LocalTime session;
    @Min(value = 1, message = "Preço inválido")
    private BigDecimal price;
    @Min(value = 1, message = "Quantidade inválida")
    private Integer quantity;
}
