package com.project.library.exception;

public enum ExceptionMessages {
    BOOK_NOT_FOUND("Book wasn't found.");

    String text;

    ExceptionMessages(String text) {
        this.text = text;
    }
}
