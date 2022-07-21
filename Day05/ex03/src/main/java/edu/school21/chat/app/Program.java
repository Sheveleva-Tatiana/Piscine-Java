package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws SQLException {
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(new JdbcDataSource().getDataSource());
        Optional<Message> messageOptional = messagesRepository.findById(11L);

        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Hello, I am " + message.getAuthor().getLogin());
            message.setLocalDateTime(LocalDateTime.now());
            messagesRepository.update(message);
            messageOptional = messagesRepository.findById(11L);
            System.out.println(messageOptional.get().getText());
            messagesRepository.save(message);
        }
    }
}
