package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;

import java.util.List;

public interface RoomsService {
    void createRoom(Chatroom room);
    List<Chatroom> showAllRooms();
}
