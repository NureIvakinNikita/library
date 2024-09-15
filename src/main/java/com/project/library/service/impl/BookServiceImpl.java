package com.project.library.service.impl;

import com.project.library.exception.BookNotFoundException;
import com.project.library.exception.ExceptionMessages;
import com.project.library.model.entity.Book;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> optionalBook = bookRepository.getBookById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.toString());
        }
    }

    @Override
    public Book getBookByAuthor(String author) {
        Optional<Book> optionalBook = bookRepository.getBookByAuthor(author);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.toString());
        }
    }

    @Override
    public Book addBook(Book newBook) {
        Book tempBook = Book.builder()
                .author(newBook.getAuthor())
                .genre(newBook.getGenre())
                .title(newBook.getTitle())
                .ISBN(newBook.getISBN()).build();
        return bookRepository.save(tempBook);
    }

    @Override
    public Book updateBook(Book updatedBook) {
        Book tempBook;
        if (bookRepository.existsById(updatedBook.getId())) {
            tempBook = Book.builder()
                    .id(updatedBook.getId())
                    .author(updatedBook.getAuthor())
                    .genre(updatedBook.getGenre())
                    .title(updatedBook.getTitle())
                    .ISBN(updatedBook.getISBN()).build();
            return bookRepository.save(tempBook);
        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.toString());
        }
    }

    @Override
    public void deleteBookById(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.toString());
        }
    }
}
