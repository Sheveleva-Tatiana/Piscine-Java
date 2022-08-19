package edu.school21.sockets.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {

    private Long id;
    private String title;
    private String owner;
    private List<Message> messages;

    public Chatroom(String title, String owner) {
        this.title = title;
        this.owner = owner;
    }

    public Chatroom() {

    }

    public Chatroom(Long id, String title, String owner, List<Message> messages) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.messages = messages;
    }

    public Chatroom(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return Objects.equals(id, chatroom.id) && Objects.equals(title, chatroom.title) && Objects.equals(owner, chatroom.owner) && Objects.equals(messages, chatroom.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, owner, messages);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", owner=" + owner +
                ", messages=" + messages +
                '}';
    }
}
