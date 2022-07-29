package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;

@Component("usersService")
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;
    private Long id = 0L;

    @Autowired
    public UsersServiceImpl(@Qualifier("jdbcTemplateRepository") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        UUID password = UUID.randomUUID();

        if (email == null || email.isEmpty()) {
            System.err.println("Error: email not specified");
            return null;
        }
        usersRepository.save(new User(++id, email, password.toString()));
        return password.toString();
    }
}