package com.glauber.cinema.Ticket.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.glauber.cinema.Ticket.exception.ChairNotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String movieName;
    @Column
    private String name;
    @Column
    private Integer number;
    @JsonManagedReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Chair> chairs = new ArrayList<>();

    public Chair getChairAt(String chairLine, int chairNumber) {
        return this.chairs.stream()
                .filter(it -> it.getLine().equals(chairLine) && it.getNumber() == chairNumber)
                .findFirst().orElseThrow(ChairNotFoundException::new);
    }
}
