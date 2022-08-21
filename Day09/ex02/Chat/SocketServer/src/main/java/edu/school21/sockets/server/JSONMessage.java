package edu.school21.sockets.server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JSONMessage {
    private String messageJSON;
    private LocalDateTime timeJSON;

    private static  final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public JSONMessage() {
    }

    public String getMessageJSON() {
        return messageJSON;
    }

    public void setMessageJSON(String messageJSON) {
        this.messageJSON = messageJSON;
    }

    public LocalDateTime getTimeJSON() {
        return timeJSON;
    }

    public void setTimeJSON(LocalDateTime timeJSON) {
        this.timeJSON = timeJSON;
    }
}
