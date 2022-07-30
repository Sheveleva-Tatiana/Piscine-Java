package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MessagesRepositoryImpl implements MessagesRepository {
    private JdbcTemplate jdbcTemplate;
    private final String alQuery = "SELECT * FROM server.message";
    private final String inQuery = "INSERT INTO server.message (message) VALUES (?)";

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server;");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.message (\n" +
                "message text not null,\n" +
                "time timestamp default current_timestamp);");
    }

    private class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int i) throws SQLException {
            Message message = new Message();

            message.setMessage(rs.getString("message"));
            message.setTime(rs.getTimestamp("time").toLocalDateTime());
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
        int i = jdbcTemplate.update(inQuery, entity.getMessage());

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
}

