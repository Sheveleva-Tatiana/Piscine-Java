package edu.school21.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int identifier;
    private String login;
    private String password;
    private boolean isAuthenticated;

}
