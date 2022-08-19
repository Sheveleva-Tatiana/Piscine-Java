package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
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
public class RoomsRepositoryImpl implements RoomsRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomsRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.rooms (\n" +
                "id serial primary key,\n" +
                "title varchar(40) not null unique, \n" +
                "owner varchar(100) not null);");
    }

    @Override
    public Chatroom findById(Long id) {
        String idQuery = "SELECT * FROM server.rooms WHERE id = ?";
        Chatroom room = jdbcTemplate.query(idQuery,
                new Object[]{id},
                new int[]{Types.INTEGER},
                new BeanPropertyRowMapper<>(Chatroom.class)).stream().findAny().orElse(null);
        return room;
    }

    @Override
    public List<Chatroom> findAll() {
        String alQuery = "SELECT * FROM server.rooms";
        List<Chatroom> rooms = jdbcTemplate.query(alQuery, new BeanPropertyRowMapper<>(Chatroom.class));
        return rooms;
    }

    @Override
    public void save(Chatroom entity) {
        String inQuery = "INSERT INTO server.rooms (title, owner) VALUES (?, ?)";
        int i = jdbcTemplate.update(inQuery, entity.getTitle(), entity.getOwner());

        if (i == 0) {
            System.err.println("Room wasn't saved: " + entity);
        }
    }

    @Override
    public void update(Chatroom entity) {
        String upQuery = "UPDATE server.rooms SET title = ?, owner = ? WHERE id = ?";
        int i = jdbcTemplate.update(upQuery, entity.getTitle(),
                entity.getOwner(), entity.getId());

        if (i == 0) {
            System.err.println("Room wasn't updated: " + entity);
        }
    }

    @Override
    public void delete(Long id) {
        String dlQuery = "DELETE FROM server.rooms WHERE id = ?";
        int i = jdbcTemplate.update(dlQuery, id);

        if (i == 0) {
            System.err.println("Room not found with id: " + id);
        }
    }

    @Override
    public Optional<Chatroom> findByTitle(String title) {
        String usQuery = "SELECT * FROM server.rooms WHERE title = ?";

        Chatroom room = jdbcTemplate.query(usQuery,
                new Object[]{title},
                new int[]{Types.VARCHAR},
                new BeanPropertyRowMapper<>(Chatroom.class)).stream().findAny().orElse(null);
        return Optional.ofNullable(room);
    }
}
