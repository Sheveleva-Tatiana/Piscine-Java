package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Message> {
    List<Message> findAllByRoom(String roomTitle);
    Message findLastRoomByAuthor(String author);
}
