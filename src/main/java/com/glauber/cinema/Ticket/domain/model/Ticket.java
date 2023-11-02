package com.glauber.cinema.Ticket.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

//@JsonManagedReference is the forward part of reference, the one that gets serialized normally.
//@JsonBackReference is the back part of reference; it’ll be omitted from serialization.

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate createAt;
    @Column
    private LocalDate ticketDay;
    @Column
    private String number;
    @Column
    private BigDecimal price;
    @Column
    private LocalTime session;
    @OneToOne
    @JoinColumn(name = "id_chair")
    private Chair chair;
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_purchase")
    private Purchase purchase;
    @OneToOne
    @JoinColumn(name = "id_room")
    private Room room;

    public Ticket(Room room, LocalDate ticketDay, LocalTime session, BigDecimal price, Purchase purchase) {
        this.room = room;
        this.ticketDay = ticketDay;
        this.session = session;
        this.price = price;
        this.purchase = purchase;
    }
    public static Ticket of(Purchase purchase, Room room) {
        var ticket = new Ticket(room, purchase.getDate(), purchase.getSession(), purchase.getPrice(), purchase);

        var chair = room.getChairAt(purchase.getChairLine(), purchase.getChairNumber());
        ticket.setChair(chair);
        ticket.setPrice(purchase.getPrice());
        String body = generateDescriptionToNumber(ticket, purchase);
        ticket.setNumber(body);
        return ticket;
    }
    private static String generateDescriptionToNumber(Ticket ticket, Purchase purchase) {
        String tickerDescription = ticket.getTicketDay().getYear() + "-" + purchase.getSession() + "-" + ticket.getRoom().getNumber();

        // TODO: Gerando um UUId individual para cada ticket
        tickerDescription += "-" + generateUniqueIdentifier();

        return tickerDescription;
    }

    private static String generateUniqueIdentifier() {
        return UUID.randomUUID().toString();
    }
}
