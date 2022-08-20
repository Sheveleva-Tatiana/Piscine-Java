package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import java.util.List;

public interface UsersService {
    boolean signIn(String username, String password);
    void signUp(User user);
    void createMessage(String message, String author, String title);
    List<Message> getAllMessageByTitle(String title);
    Message findLastRoom(String author);
}
