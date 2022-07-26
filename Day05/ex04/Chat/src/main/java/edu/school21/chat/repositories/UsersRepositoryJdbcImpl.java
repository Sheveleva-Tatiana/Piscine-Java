package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersRepositoryJdbcImpl implements UsersRepository{
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<User> findAll(int page, int size) {
        String allQuery = "SELECT u.*, c.id, c.title, uc.chat_id, ct.title, us.id, us.name, us.password\n" +
                "FROM (SELECT * FROM chat.user LIMIT ? OFFSET ?) u\n" +
                "LEFT JOIN chat.chatroom c ON u.id = c.owner\n" +
                "LEFT JOIN chat.user_chatroom uc ON u.id = uc.user_id\n" +
                "LEFT JOIN chat.chatroom ct ON uc.chat_id = ct.id\n" +
                "LEFT JOIN chat.user us ON ct.owner = us.id\n" +
                "ORDER BY u.id, c.id, uc.chat_id;";
        List<User> users = new ArrayList<>();
        int offset = page * size;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement st = connection.prepareStatement(allQuery)) {
            st.setLong(1, size);
            st.setLong(2, offset);
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                final Long userId, createdChatId, usedChatId;
                User user;
                Chatroom chat;
                userId = rs.getLong(1);
                if (users.stream().noneMatch(u -> userId.equals(u.getId()))) {
                    user = new User(userId, rs.getString(2), rs.getString(3),
                            new ArrayList<>(), new ArrayList<>());
                    users.add(user);
                } else {
                    user = users.stream().filter(u -> userId.equals(u.getId())).collect(Collectors.toList()).get(0);
                }
                createdChatId = rs.getLong(4);

                if (createdChatId != 0 && user.getCreatedRooms().stream()
                        .noneMatch(c -> createdChatId.equals(c.getId()))) {
                    chat = new Chatroom(createdChatId, rs.getString(5),
                            new User(user.getId(), user.getLogin(), user.getPassword()), null);
                    user.getCreatedRooms().add(chat);
                }
                usedChatId = rs.getLong(6);

                if (usedChatId != 0 && user.getUsedRooms().stream()
                        .noneMatch(c -> usedChatId.equals(c.getId()))) {
                    chat = new Chatroom(usedChatId, rs.getString(7),
                            new User(rs.getLong(8), rs.getString(9),
                                    rs.getString(10)), null);
                    user.getUsedRooms().add(chat);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
