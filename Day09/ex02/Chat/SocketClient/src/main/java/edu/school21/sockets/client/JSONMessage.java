package edu.school21.sockets.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JSONMessage {
    private String message;
    private LocalDateTime time;

    private static  final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public JSONMessage() {
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
}
