package com.project.library.service;

import com.project.library.model.entity.Book;

import java.util.List;


public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(long id);

    Book getBookByTitle(String title);

    Book getBookByGenre(String genre);

    Book getBookByAuthor(String author);

    List<Book> getBooksBySearch(String author, String title, String genre);

    Book addBook(Book newBook);

    Book updateBook(Book updatedBook);

    void deleteBookById(long id);

}
