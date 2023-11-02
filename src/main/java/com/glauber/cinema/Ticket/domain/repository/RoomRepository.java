package com.glauber.cinema.Ticket.domain.repository;

import com.glauber.cinema.Ticket.domain.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}