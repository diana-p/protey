package com.test.protey.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}
