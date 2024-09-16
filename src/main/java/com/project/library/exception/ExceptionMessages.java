package com.project.library.exception;

public enum ExceptionMessages {
    BOOK_NOT_FOUND("Book wasn't found."),
    BOOK_ISBN_NOT_UNIQUE("Book with such ISBN already exists."),
    BOOK_DATE_CANT_BE_IN_FUTURE("The year of publication cannot be greater than the current year."),
    BOOK_UPDATE_EXCEPTION("Something went wrong updating book."),
    BOOK_ADD_EXCEPTION("Something went wrong saving book to db."),
    BOOK_DELETE_EXCEPTION("Something went wrong deleting book.");

    String text;

    ExceptionMessages(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
