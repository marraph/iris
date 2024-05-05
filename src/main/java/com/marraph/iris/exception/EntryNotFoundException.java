package com.marraph.iris.exception;

public class EntryNotFoundException extends Exception {

    public EntryNotFoundException(Long id) {
        super("Entry not found: " + id + "!");
    }

    public EntryNotFoundException() {
        super("Entry not found!");
    }

}
