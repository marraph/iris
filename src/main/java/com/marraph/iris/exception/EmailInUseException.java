package com.marraph.iris.exception;

public class EmailInUseException extends RuntimeException {

    public EmailInUseException(String email) {
        super("Email: " + email + " already in use!");
    }
}