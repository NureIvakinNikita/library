package com.project.library.exception;

public class BookDeleteException extends RuntimeException{
    public BookDeleteException(String message) {
        super(message);
    }

    public BookDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
