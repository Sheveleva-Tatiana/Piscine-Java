package edu.school21.sockets.models;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String message;
    private String author;
    private String titleRoom;
    private LocalDateTime time;

    private static  final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitleRoom() {
        return titleRoom;
    }

    public void setTitleRoom(String titleRoom) {
        this.titleRoom = titleRoom;
    }

    public Message(String message, String author, String titleRoom) {
        this.message = message;
        this.author = author;
        this.titleRoom = titleRoom;
    }

    public Message(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", author='" + author + '\'' +
                ", titleRoom='" + titleRoom + '\'' +
                ", time=" + time.format(FORMATTER) +
                '}';
    }
}
