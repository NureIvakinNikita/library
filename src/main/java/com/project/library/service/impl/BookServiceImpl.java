package com.project.library.service.impl;

import com.project.library.exception.*;
import com.project.library.model.entity.Book;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.library.exception.ExceptionMessages.*;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> optionalBook = bookRepository.getBookById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.getText());
        }
    }

    @Override
    public Page<Book> getBookByAuthor(String author, Pageable pageable) {
        return bookRepository.getBookByAuthor(author, pageable);
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
    public Page<Book> getBookByGenre(String genre, Pageable pageable) {
        return bookRepository.getBookByGenre(genre, pageable);
    }

    @Override
    public Page<Book> getBooksBySearch(String author, String title, String genre, Pageable pageable) {
        final String finalAuthor = (author == null || author.isEmpty()) ? "Unknown" : author;
        final String finalTitle = (title == null || title.isEmpty()) ? "Unknown" : title;
        final String finalGenre = (genre == null || genre.isEmpty()) ? "Unknown" : genre;

        List<Book> allBooks = bookRepository.findAll();

        List<Book> filteredBooks = allBooks.stream()
                .filter(book -> (finalAuthor.equals("Unknown") || finalAuthor.equals(book.getAuthor())) &&
                        (finalTitle.equals("Unknown") || finalTitle.equals(book.getTitle())) &&
                        (finalGenre.equals("Unknown") || finalGenre.equals(book.getGenre())))
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredBooks.size());
        int end = Math.min((start + pageable.getPageSize()), filteredBooks.size());
        Page<Book> page = new PageImpl<>(filteredBooks.subList(start, end), pageable, filteredBooks.size());

        return page;
    }

    @Override
    @Transactional
    public Book addBook(Book newBook) {
        if (!checkISBN(newBook, 1)) throw new BookArgumentsViolationException("" +
                ExceptionMessages.BOOK_ISBN_NOT_UNIQUE.getText()
        );
        try {
            Book tempBook = Book.builder()
                    .author(newBook.getAuthor())
                    .genre(newBook.getGenre())
                    .publicationYear(newBook.getPublicationYear())
                    .title(newBook.getTitle())
                    .ISBN(newBook.getISBN()).build();
            return bookRepository.save(tempBook);
        } catch (Exception e) {
            throw new BookAddException(BOOK_ADD_EXCEPTION.getText() + "\n" + e.getMessage());
        }

    }

    @Override
    @Transactional
    public Book updateBook(Book updatedBook) {
        Book tempBook;
        if (!checkISBN(updatedBook, 0)) throw new BookArgumentsViolationException("" +
                ExceptionMessages.BOOK_ISBN_NOT_UNIQUE.getText()
        );
        if (bookRepository.existsById(updatedBook.getId())) {
            try {
                tempBook = Book.builder()
                        .id(updatedBook.getId())
                        .author(updatedBook.getAuthor())
                        .publicationYear(updatedBook.getPublicationYear())
                        .genre(updatedBook.getGenre())
                        .title(updatedBook.getTitle())
                        .ISBN(updatedBook.getISBN()).build();
                return bookRepository.save(tempBook);
            } catch (Exception e) {
                throw new BookUpdateException(BOOK_UPDATE_EXCEPTION.getText() + "\n" + e.getMessage());
            }

        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.getText());
        }
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        if (bookRepository.existsById(id)) {
            try {
                bookRepository.deleteById(id);
            } catch (Exception e) {
                throw new BookDeleteException(BOOK_DELETE_EXCEPTION.getText() + "\n" + e.getMessage());
            }

        } else {
            throw new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND.getText());
        }
    }

    private boolean checkISBN(Book toCheck, int f) {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            if (f==0 && book.getId()!= toCheck.getId() && book.getISBN().equals(toCheck.getISBN())) {
                return false;
            } else if (f == 1 && book.getISBN().equals(toCheck.getISBN())) {
                return false;
            }
        }
        return true;
    }
}
