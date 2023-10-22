package com.glauber.cinema.Ticket.service;

import com.glauber.cinema.Ticket.domain.model.Chair;
import com.glauber.cinema.Ticket.domain.model.Purchase;

public interface RoomService {
    Chair getChair(Purchase purchase);
}
