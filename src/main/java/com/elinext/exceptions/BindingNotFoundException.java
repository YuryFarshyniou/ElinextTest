package com.elinext.exceptions;

public class BindingNotFoundException extends RuntimeException {
    public BindingNotFoundException() {
    }

    public BindingNotFoundException(String message) {
        super(message);
    }

    public BindingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
