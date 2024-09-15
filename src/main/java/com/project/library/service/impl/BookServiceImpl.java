package com.project.library.service.impl;

import com.project.library.exception.BookArgumentsViolationException;
import com.project.library.exception.BookNotFoundException;
import com.project.library.exception.ExceptionMessages;
import com.project.library.model.entity.Book;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookService;
import com.sun.jdi.connect.Connector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.name());
        }
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        return bookRepository.getBookByAuthor(author);
    }

    @Override
    public Book getBookByTitle(String title) {
        Optional<Book> optionalBook = bookRepository.getBookByTitle(title);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.getText());
        }
    }

    @Override
    public List<Book> getBookByGenre(String genre) {
        return bookRepository.getBookByGenre(genre);
    }

    @Override
    public List<Book> getBooksBySearch(String author, String title, String genre) {
        final String finalAuthor = (author == null || author.isEmpty()) ? "Unknown" : author;
        final String finalTitle = (title == null || title.isEmpty()) ? "Unknown" : title;
        final String finalGenre = (genre == null || genre.isEmpty()) ? "Unknown" : genre;

        List<Book> allBooks = bookRepository.findAll();

        return allBooks.stream()
                .filter(book -> (finalAuthor.equals("Unknown") || finalAuthor.equals(book.getAuthor())) &&
                        (finalTitle.equals("Unknown") || finalTitle.equals(book.getTitle())) &&
                        (finalGenre.equals("Unknown") || finalGenre.equals(book.getGenre())))
                .collect(Collectors.toList());
    }

    @Override
    public Book addBook(Book newBook) {
        Book tempBook = Book.builder()
                .author(newBook.getAuthor())
                .genre(newBook.getGenre())
                .publicationYear(newBook.getPublicationYear())
                .title(newBook.getTitle())
                .ISBN(newBook.getISBN()).build();
        return bookRepository.save(tempBook);
    }

    @Override
    public Book updateBook(Book updatedBook) {
        Book tempBook;
        if (!checkISBN(updatedBook)) throw new BookArgumentsViolationException("" +
                ExceptionMessages.BOOK_ISBN_NOT_UNIQUE.getText()
        );
        if (bookRepository.existsById(updatedBook.getId())) {
            tempBook = Book.builder()
                    .id(updatedBook.getId())
                    .author(updatedBook.getAuthor())
                    .publicationYear(updatedBook.getPublicationYear())
                    .genre(updatedBook.getGenre())
                    .title(updatedBook.getTitle())
                    .ISBN(updatedBook.getISBN()).build();
            return bookRepository.save(tempBook);
        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.getText());
        }
    }

    @Override
    public void deleteBookById(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.getText());
        }
    }

    private boolean checkISBN(Book toCheck) {
        List<Book> books = getAllBooks();
        for (Book book : books) {
            if (book.getId()!= toCheck.getId() && book.getISBN().equals(toCheck.getISBN())) {
                return false;
            }
        }
        return true;
    }
}
