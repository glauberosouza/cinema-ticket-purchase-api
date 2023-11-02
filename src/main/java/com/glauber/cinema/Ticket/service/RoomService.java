package com.glauber.cinema.Ticket.service;

import com.glauber.cinema.Ticket.domain.model.Chair;
import com.glauber.cinema.Ticket.domain.model.Purchase;
import com.glauber.cinema.Ticket.domain.model.Room;

public interface RoomService {
    Chair getChair(Purchase purchase);
    Room findRoom(Long id);
}
