package com.project.library.exception;

public class BookAddException extends RuntimeException{
    public BookAddException(String message) {
        super(message);
    }

    public BookAddException(String message, Throwable cause) {
        super(message, cause);
    }
}