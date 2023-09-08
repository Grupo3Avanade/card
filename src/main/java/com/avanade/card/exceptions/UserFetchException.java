package com.avanade.card.exceptions;

public class UserFetchException extends RuntimeException {
    public UserFetchException(String message) {
        super(message);
    }

    public UserFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
