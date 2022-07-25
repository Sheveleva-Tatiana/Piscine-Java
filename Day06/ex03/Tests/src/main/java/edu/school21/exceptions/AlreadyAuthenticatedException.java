package edu.school21.exceptions;

public class AlreadyAuthenticatedException extends Exception {
    public AlreadyAuthenticatedException(String message) {
        super(message);
    }
}
