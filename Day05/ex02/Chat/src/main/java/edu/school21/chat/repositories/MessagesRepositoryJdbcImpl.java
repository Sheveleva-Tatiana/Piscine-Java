package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    private final String mQuery = "SELECT * FROM chat.message WHERE id = ";

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String mQuery = "SELECT * FROM chat.message WHERE id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(mQuery);

            if (!rs.next()) {
                return null;
            }
            Long userId = rs.getLong(2);
            Long roomId = rs.getLong(3);
            User user = findUser(userId);
            Chatroom room = findChat(roomId);
            return Optional.of(new Message(rs.getLong(1), user, room,
                    rs.getString(4), rs.getTimestamp(5).toLocalDateTime()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    private User findUser(Long id) throws SQLException {
        String uQuery = "SELECT * FROM chat.user WHERE id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(uQuery);
            if (!rs.next()) {
                return null;
            }
            return new User(id, rs.getString(2), rs.getString(3));
        }
    }

    private Chatroom findChat(Long id) throws SQLException {
        String cQuery = "SELECT * FROM chat.chatroom WHERE id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(cQuery);
            if (!rs.next()) {
                return null;
            }
            return new Chatroom(id, rs.getString(2));
        }
    }

    @Override
    public void save(Message message) {
        Long userId, roomId;
        String localDateTime = "'null'";

        if (checkMessage(message)) {
            userId = message.getAuthor().getId();
            roomId = message.getRoom().getId();
            try(Connection con = dataSource.getConnection();
                Statement st = con.createStatement()) {
                String uQuery = "SELECT * FROM chat.user WHERE id = ";
                ResultSet rs = st.executeQuery(uQuery + userId);

                if (!rs.next()) {
                    throw new NotSavedSubEntityException("User with id = " + userId + " doesn't exist");
                }
                String cQuery = "SELECT * FROM chat.chatroom WHERE id = ";
                rs = st.executeQuery(cQuery + roomId);

                if (!rs.next()) {
                    throw new NotSavedSubEntityException("Chatroom with id = " + roomId + " doesn't exist");
                }

                if (message.getLocalDateTime() != null) {
                    localDateTime = "'" + Timestamp.valueOf(message.getLocalDateTime()) + "'";
                }
                rs = st.executeQuery("INSERT INTO chat.message (author, room, text, ldatetime)" +
                        "VALUES (" + userId + ", " + roomId + ", '" + message.getText() + "', " + localDateTime + ") RETURNING id");

                if (!rs.next()) {
                    throw new NotSavedSubEntityException("Internal Error");
                }
                message.setId(rs.getLong(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkMessage(Message message) {
        if (message.getAuthor() == null || message.getAuthor().getId() == null) {
            return false;
        }

        if (message.getRoom() == null || message.getRoom().getId() == null) {
            return false;
        }

        if (message.getRoom().getOwner() == null || message.getRoom().getOwner().getId() == null) {
            return false;
        }

        if (message.getText() == null || message.getText().length() == 0) {
            return false;
        }
        return true;
    }
}
