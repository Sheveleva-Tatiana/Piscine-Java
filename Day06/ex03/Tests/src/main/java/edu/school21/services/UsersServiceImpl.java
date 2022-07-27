package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws EntityNotFoundException, AlreadyAuthenticatedException {
        User user = usersRepository.findByLogin(login);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException("No such user");
        }
        if (user.isAuthenticated())
            throw new AlreadyAuthenticatedException("Authenticate error");
        boolean isAuthenticated = user.getPassword().equals(password);
        if (isAuthenticated) {
            user.setAuthenticated(true);
            usersRepository.update(user);
        }
        return isAuthenticated;
    }
}
