package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.RoomsRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class RoomsServiceImpl implements RoomsService {
    private RoomsRepository roomsRepository;

    @Autowired
    public RoomsServiceImpl(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    @Override
    public void createRoom(Chatroom room) {
        if (roomsRepository.findByTitle(room.getTitle()).isPresent()) {
            throw new RuntimeException("Room: " + room.getTitle() + " already exist");
        }
        roomsRepository.save(room);
    }

    @Override
    public List<Chatroom> showAllRooms() {
       return roomsRepository.findAll();

    }
}
