package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws SQLException {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        updateData("schema.sql", jdbcDataSource);
        updateData("data.sql", jdbcDataSource);
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(jdbcDataSource.getDataSource());

        System.out.println("\n1)Правильная работа");
        Optional<Message> messageOptional = messagesRepository.findById(1L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Hello, I am not " + message.getAuthor().getLogin());
            message.setLocalDateTime(null);
            messagesRepository.update(message);
            messageOptional = messagesRepository.findById(1L);
            System.out.println("Печатаем обновленное сообщение:\n" + messageOptional.get().getText());
        } else {
            System.err.println("\nMessage not found!");
        }
        System.out.println("\n2)Заменяем автора на другого с существующим ID");
        messageOptional = messagesRepository.findById(2L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setAuthor(new User(3L, "Login", "Password", new ArrayList<>(), new ArrayList<>()));
            message.setText("Hello, I am a new author");
            message.setLocalDateTime(LocalDateTime.now());
            messagesRepository.update(message);
            messageOptional = messagesRepository.findById(2L);
            System.out.println("Печатаем обновленное сообщение:\n" + messageOptional.get().getText());
            System.out.println("Печатаем Login автора:\n" + messageOptional.get().getAuthor().getLogin() + " и Id :" +
                    messageOptional.get().getAuthor().getId());
        } else {
            System.err.println("\nMessage not found!");
        }
        System.out.println("\n3)Заменяем автора на другого с несуществующим ID");
        messageOptional = messagesRepository.findById(2L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setAuthor(new User(15L, "Login", "Password", new ArrayList<>(), new ArrayList<>()));
            message.setText("Now, should be a new message");
            message.setLocalDateTime(LocalDateTime.now());
            messagesRepository.update(message);
            messageOptional = messagesRepository.findById(2L);
            System.out.println("Печатаем сообщение:\n" + messageOptional.get().getText());
        } else {
            System.err.println("\nMessage not found!");
        }

        System.out.println("\n4)Пытаемся заменить несуществующее сообщение");
        messageOptional = messagesRepository.findById(22L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setAuthor(new User(15L, "Login", "Password", new ArrayList<>(), new ArrayList<>()));
            message.setText("Now, should be a new message");
            message.setLocalDateTime(LocalDateTime.now());
            messagesRepository.update(message);
            messageOptional = messagesRepository.findById(1L);
            System.out.println("Печатаем сообщение:\n" + messageOptional.get().getText());
        } else {
            System.out.println("Message not found!");
        }
        System.out.println("\n5)Пытаемся заменить несуществующее сообщение передав в метод update");
        User user = new User(1L, "User", "User", new ArrayList<>(), new ArrayList<>());
        Chatroom chat = new Chatroom(1L, "Chat1");
        Message message = new Message(22L, user, chat, "NEW MESSAGE HERE", LocalDateTime.now());
        messagesRepository.update(message);
        messageOptional = messagesRepository.findById(message.getId());
        System.out.println(messageOptional.get().getText() + "\nwith ID = " + messageOptional.get().getId());
    }

    private static void updateData(String file, JdbcDataSource dataSource) {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            InputStream input = Program.class.getClassLoader().getResourceAsStream(file);
            Scanner scanner = new Scanner(input).useDelimiter(";");

            while (scanner.hasNext()) {
                st.executeUpdate(scanner.next().trim());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
