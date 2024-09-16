package com.project.library.service;

import com.project.library.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    Page<Book> getAllBooks(Pageable pageable);

    Book getBookById(long id);

    Book getBookByTitle(String title);

    Page<Book> getBookByGenre(String genre, Pageable pageable);

    Page<Book> getBookByAuthor(String author, Pageable pageable);

    Page<Book> getBooksBySearch(String author, String title, String genre, Pageable pageable);

    Book addBook(Book newBook);

    Book updateBook(Book updatedBook);

    void deleteBookById(long id);

}
