package com.marraph.iris.exception;

public class ConnectEntryException extends RuntimeException {

    public ConnectEntryException(String message) {
        super("Can't establish relation: " + message);
    }

}