package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MessagesRepositoryImpl implements MessagesRepository {
    private JdbcTemplate jdbcTemplate;
    private final String alQuery = "SELECT * FROM server.message";
    private final String inQuery = "INSERT INTO server.message (message, author, titleRoom) VALUES (?, ?, ?)";

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.message (\n" +
                "message text not null,\n" +
                "author varchar(100) not null,\n" +
                "titleroom varchar(40) not null, \n" +
                "time timestamp default current_timestamp);");

    }



    private class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int i) throws SQLException {
            Message message = new Message();

            message.setMessage(rs.getString("message"));
            message.setTime(rs.getTimestamp("time").toLocalDateTime());
            message.setAuthor(rs.getString("author"));
            message.setTitleRoom(rs.getString("titleRoom"));
            return message;
        }
    }

    @Override
    public Message findById(Long id) {
        return null;
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = jdbcTemplate.query(alQuery, new MessageRowMapper());
        return messages;
    }

    @Override
    public void save(Message entity) {
        int i = jdbcTemplate.update(inQuery, entity.getMessage(), entity.getAuthor(), entity.getTitleRoom());

        if (i == 0) {
            System.err.println("Message wasn't saved: " + entity);
        }
    }

    @Override
    public void update(Message entity) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<Message> findAllByRoom(String roomTitle) {
        List<Message> message = new ArrayList<>();
        try {
            String usQuery = "with t as (SELECT * FROM server.message WHERE titleroom = '" + roomTitle + "' ORDER BY time DESC LIMIT 30) \n " +
                    "select * from t order by time asc";
            message = jdbcTemplate.query(usQuery, new BeanPropertyRowMapper<>(Message.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return message;
    }

    @Override
    public Message findLastRoomByAuthor(String author) {
        List<Message> message = new ArrayList<>();
        try {
            String usQuery = "SELECT * FROM server.message WHERE author = '" + author + "' ORDER BY time DESC LIMIT 1";
            message = jdbcTemplate.query(usQuery, new BeanPropertyRowMapper<>(Message.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (message.isEmpty())
            return null;
        return message.get(0);
    }
}

