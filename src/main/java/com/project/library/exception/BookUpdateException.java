package com.project.library.exception;

public class BookUpdateException extends RuntimeException{
    public BookUpdateException(String message) {
        super(message);
    }

    public BookUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}