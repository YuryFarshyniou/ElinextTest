package com.elinext.exceptions;

public class TooManyConstructorsException extends RuntimeException{
    public TooManyConstructorsException() {
    }

    public TooManyConstructorsException(String message) {
        super(message);
    }

    public TooManyConstructorsException(String message, Throwable cause) {
        super(message, cause);
    }
}
