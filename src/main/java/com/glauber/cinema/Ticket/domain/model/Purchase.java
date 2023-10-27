package com.glauber.cinema.Ticket.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.glauber.cinema.Ticket.controller.request.PurchaseUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private Integer roomNumber;
    @Transient
    private String chairLine;
    @Transient
    private Integer chairNumber;
    @Transient
    private LocalDate date;
    @Transient
    private LocalTime session;
    @Column
    private BigDecimal price;
    @Column
    private Integer quantity;
    @OneToMany(mappedBy = "purchase", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    private List<Ticket> tickets;

    public Purchase(Long id, Integer roomNumber, String chairLine, Integer chairNumber, LocalDate date, LocalTime session, BigDecimal price, Integer quantity) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.chairLine = chairLine;
        this.chairNumber = chairNumber;
        this.date = date;
        this.session = session;
        this.price = price;
        this.quantity = quantity;
    }
    public static Purchase updateValues(PurchaseUpdateRequest request, Purchase purchaseToUpdate){
        purchaseToUpdate.setPrice(request.getPrice());
        purchaseToUpdate.setQuantity(request.getQuantity());
        return purchaseToUpdate;
    }

    public Chair getChair() {
        return tickets.isEmpty() ? null : tickets.get(0).getChair();
    }
}
