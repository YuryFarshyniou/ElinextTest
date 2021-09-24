package com.elinext.exceptions;

public class ConstructorNotFoundException extends RuntimeException {
    public ConstructorNotFoundException() {
    }

    public ConstructorNotFoundException(String message) {
        super(message);
    }

    public ConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
