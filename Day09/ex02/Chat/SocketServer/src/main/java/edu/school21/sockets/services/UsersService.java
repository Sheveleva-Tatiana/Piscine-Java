package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

public interface UsersService {
    boolean signIn(String username, String password);
    void signUp(User user);
    void createMessage(String message);
}
