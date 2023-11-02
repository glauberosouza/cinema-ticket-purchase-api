package com.glauber.cinema.Ticket.service.impl;

import com.glauber.cinema.Ticket.domain.model.Chair;
import com.glauber.cinema.Ticket.domain.model.Purchase;
import com.glauber.cinema.Ticket.domain.model.Room;
import com.glauber.cinema.Ticket.domain.repository.RoomRepository;
import com.glauber.cinema.Ticket.exception.ChairNotFoundException;
import com.glauber.cinema.Ticket.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {

        this.roomRepository = roomRepository;
    }

    @Override
    public Room findRoom(Long id) {
        Optional<Room> roomById = roomRepository.findById(id);
        if (roomById.isEmpty()){
            throw new NoSuchElementException("A sala com o id:" + id + " Não foi encontrada!");
        }
        Room room = roomById.get();
        return room;
    }

    @Override
    public Chair getChair(Purchase purchase) {
        var chairLine = purchase.getChairLine();
        var chairNumber = purchase.getChairNumber();
        var room = roomRepository.findByNumber(purchase.getRoomNumber());

        return room.getChairs()
                .stream()
                .filter(it -> chairLine.equals(it.getLine()) && chairNumber == it.getNumber())
                .findFirst().orElseThrow(ChairNotFoundException::new);
    }
}
