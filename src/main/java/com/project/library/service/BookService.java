package com.project.library.service;

import com.project.library.model.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(long id);

    Book getBookByAuthor(String author);

    Book addBook(Book newBook);

    Book updateBook(Book updatedBook);

    void deleteBookById(long id);

}
