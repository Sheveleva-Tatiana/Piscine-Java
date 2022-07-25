package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

public class UsersServiceImplTest {
    private final String CORRECT_LOGIN = "CORRECT_LOGIN";
    private final String INCORRECT_LOGIN = "INCORRECT_LOGIN";
    private final String CORRECT_PASSWORD = "CORRECT_PASSWORD";
    private User user;

    private final UsersRepository usersRepository = mock(UsersRepository.class);
    private final UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);

    @BeforeEach()
    public void init() {
        user = new User(1, CORRECT_LOGIN, CORRECT_PASSWORD, false);
        when(usersRepository.findByLogin(CORRECT_LOGIN)).thenReturn(user);
        doNothing().when(usersRepository).update(user);
    }

    @Test
    public void correctLoginAndPasswordTest() throws EntityNotFoundException, AlreadyAuthenticatedException  {
        Assertions.assertTrue(usersService.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
        verify(usersRepository).findByLogin(CORRECT_LOGIN);
        verify(usersRepository).update(user);
    }

    @Test
    public void isCorrectLoginTest() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> usersService.authenticate(INCORRECT_LOGIN, CORRECT_PASSWORD));
        verify(usersRepository).findByLogin(INCORRECT_LOGIN);
        verify(usersRepository, never()).update(user);
    }


    @Test
    public void isCorrectPasswordTest() throws AlreadyAuthenticatedException, EntityNotFoundException {
        String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
        Assertions.assertFalse(usersService.authenticate(CORRECT_LOGIN, INCORRECT_PASSWORD));
        verify(usersRepository).findByLogin(CORRECT_LOGIN);
        verify(usersRepository, never()).update(any());
    }
}
