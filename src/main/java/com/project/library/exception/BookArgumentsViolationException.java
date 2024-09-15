package com.project.library.exception;

public class BookArgumentsViolationException extends RuntimeException{
    public BookArgumentsViolationException(String message) {
        super(message);
    }

    public BookArgumentsViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}