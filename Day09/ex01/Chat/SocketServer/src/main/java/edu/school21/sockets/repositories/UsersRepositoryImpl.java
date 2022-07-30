package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.user (\n" +
                "id serial primary key,\n" +
                "username varchar(40) not null unique,\n" +
                "password varchar(100) not null);");
    }


    @Override
    public User findById(Long id) {
        String idQuery = "SELECT * FROM server.user WHERE id = ?";
        User user = jdbcTemplate.query(idQuery,
                new Object[]{id},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);

        return user;
    }

    @Override
    public List<User> findAll() {
        String alQuery = "SELECT * FROM server.user";
        List<User> users = jdbcTemplate.query(alQuery, new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @Override
    public void save(User entity) {
        String inQuery = "INSERT INTO server.user (username, password) VALUES (?, ?)";
        int i = jdbcTemplate.update(inQuery, entity.getUsername(), entity.getPassword());

        if (i == 0) {
            System.err.println("User wasn't saved: " + entity);
        }
    }

    @Override
    public void update(User entity) {
        String upQuery = "UPDATE server.user SET username = ?, password = ? WHERE id = ?";
        int i = jdbcTemplate.update(upQuery, entity.getUsername(),
                entity.getPassword(), entity.getId());

        if (i == 0) {
            System.err.println("User wasn't updated: " + entity);
        }
    }

    @Override
    public void delete(Long id) {
        String dlQuery = "DELETE FROM server.user WHERE id = ?";
        int i = jdbcTemplate.update(dlQuery, id);

        if (i == 0) {
            System.err.println("User not found with id: " + id);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String usQuery = "SELECT * FROM server.user WHERE username = ?";
        User user = jdbcTemplate.query(usQuery,
                new Object[]{username},
                new int[]{Types.VARCHAR},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
        return Optional.ofNullable(user);
    }
}
