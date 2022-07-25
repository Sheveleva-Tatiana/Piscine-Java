package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.repositories.UsersRepository;
import edu.school21.models.User;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

public class UsersServiceImpl {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws EntityNotFoundException, AlreadyAuthenticatedException {
        User user = findByLogin(login);
        if (user.isAuthenticated())
            throw new AlreadyAuthenticatedException("Authentication error");
        boolean isAuthenticated = user.getPassword().equals(password);
        if (isAuthenticated) {
            user.setAuthenticated(true);
            usersRepository.update(user);
        }
        return isAuthenticated;
    }

    private User findByLogin(String login) throws EntityNotFoundException {
        User user = usersRepository.findByLogin(login);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException("No such user");
        }
        return user;
    }

}
