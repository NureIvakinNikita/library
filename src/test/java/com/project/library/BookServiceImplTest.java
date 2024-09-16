package com.project.library;

import com.project.library.exception.BookArgumentsViolationException;
import com.project.library.exception.BookNotFoundException;
import com.project.library.exception.ExceptionMessages;
import com.project.library.model.entity.Book;
import com.project.library.repository.BookRepository;
import com.project.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;

    private List<Book> books;

    @BeforeEach
    private void initialize() {
        this.testBook = Book.builder()
                .id(1L)
                .author("Nikita Ivakin")
                .title("Test Book")
                .genre("Fiction")
                .publicationYear(LocalDate.of(2019, 1, 1))
                .ISBN("1234567890123")
                .build();
        Book book = Book.builder()
                .id(2L)
                .author("Nikita Doe")
                .title("Book")
                .genre("Fiction")
                .publicationYear(LocalDate.of(2019, 4, 15))
                .ISBN("1234567890555")
                .build();
        this.books = new ArrayList<>();
        books.add(testBook);
        books.add(book);
    }

    @Test
    public void getAllBooksTestShouldReturnPagedBooks() {
        Page<Book> pagedBooks = new PageImpl<>(books);
        Pageable pageable = PageRequest.of(0, 2);

        when(bookRepository.findAll(pageable)).thenReturn(pagedBooks);

        Page<Book> result = bookService.getAllBooks(pageable);
        assertThat(result.getContent()).hasSize(2).contains(testBook);

        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    public void getBookByIdShouldReturnBook() {
        when(bookRepository.getBookById(1)).thenReturn(Optional.of(testBook));

        assertThat(bookService.getBookById(1)).isEqualTo(testBook);

        verify(bookRepository, times(1)).getBookById(1);
    }

    @Test
    public void getBookByIdShouldThrowBookNotFoundException() {
        when(bookRepository.getBookById(3)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookById(3))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining(ExceptionMessages.BOOK_NOT_FOUND.getText());
        verify(bookRepository, times(1)).getBookById(3);
    }

    @Test
    public void addBookShouldReturnAddedBook(){
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book newBook = Book.builder()
                .author("Nikita Ivakin")
                .title("Test Book")
                .genre("Fiction")
                .publicationYear(LocalDate.of(2019, 1, 1))
                .ISBN("1234567890123").build();

        assertThat(bookService.addBook(newBook)).isEqualTo(testBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }



    @Test
    public void updateBookShouldReturnUpdatedBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(books.get(1));

        Book newBook = Book.builder()
                .id(1L)
                .author("Nikita Doe")
                .title("Book")
                .genre("Fiction")
                .publicationYear(LocalDate.of(2019, 4, 15))
                .ISBN("1234567890555")
                .build();

        assertThat(bookService.updateBook(newBook)).isEqualTo(books.get(1));

        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void deleteBookShouldThrowBookNotFoundException() {
        when(bookRepository.existsById(3L)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBookById(3L);
        });

        verify(bookRepository, never()).deleteById(3L);
    }

}
