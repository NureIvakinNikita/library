package com.project.library.controller;

import com.project.library.model.entity.Book;
import com.project.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/by-author")
    public Book getBookByAuthor(
            @RequestParam(value = "author") String author) {
        return bookService.getBookByAuthor(author);
    }

    @GetMapping("/by-title")
    public Book getBookByTitle(
            @RequestParam(value = "title") String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/by-genre")
    public Book getBookByGenre(
            @RequestParam(value = "genre") String genre) {
        return bookService.getBookByGenre(genre);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "genre", required = false) String genre) {
        return bookService.getBooksBySearch(author, title, genre);
    }

    @PostMapping
    public Book addBrook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteBookById(id);
    }
}
