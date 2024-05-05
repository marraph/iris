package com.marraph.iris.exception;

public class EntryAlreadyExistsException extends Exception {

    public EntryAlreadyExistsException(Long id) {
        super("Entry already exists with this id: " + id);
    }

    public EntryAlreadyExistsException(String properties) {
        super("Entry already exists with this properties: " + properties);
    }

}