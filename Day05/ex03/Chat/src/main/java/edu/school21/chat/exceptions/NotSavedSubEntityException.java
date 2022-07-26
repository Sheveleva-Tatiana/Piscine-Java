package edu.school21.chat.exceptions;

import java.sql.SQLException;

public class NotSavedSubEntityException extends SQLException {
    public NotSavedSubEntityException(String s) {
        super(s);
    }
}
