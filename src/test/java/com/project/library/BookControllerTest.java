package com.project.library;


import com.project.library.controller.BookController;
import com.project.library.model.entity.Book;
import com.project.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book testBook;

    @BeforeEach
    public void initialize() {
        this.testBook = Book.builder()
                .id(1L)
                .author("Nikita Ivakin")
                .title("Test Book")
                .genre("Fiction")
                .publicationYear(LocalDate.of(2020, 1, 1))
                .ISBN("1234567890123")
                .build();
    }

    @Test
    public void testGetBookById() throws Exception{
        when(bookService.getBookById(1L)).thenReturn(testBook);

        mockMvc.perform(get("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.author", is("Nikita Ivakin")))
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andExpect(jsonPath("$.genre", is("Fiction")))
                .andExpect(jsonPath("$.isbn", is("1234567890123")));
    }

    @Test
    public void testAddBook() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn(testBook);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Book\",\"author\":\"Nikita Ivakin\",\"genre\":\"Fiction\",\"publicationYear\":\"2020-01-01\",\"isbn\":\"1234567890123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.author", is("Nikita Ivakin")))
                .andExpect(jsonPath("$.title", is("Test Book")));
    }

    @Test
    public void testUpdateBook() throws Exception {
        when(bookService.updateBook(any(Book.class))).thenReturn(testBook);

        mockMvc.perform(put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Updated Title\",\"author\":\"Nikita Ivakin\",\"genre\":\"Fiction\",\"publicationYear\":\"2020-01-01\",\"isbn\":\"1234567890123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Book")));
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBookById(1L);

        mockMvc.perform(delete("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {
        Page<Book> booksPage = new PageImpl<>(List.of(testBook));

        when(bookService.getBookByAuthor(eq("Nikita Ivakin"), any(Pageable.class)))
                .thenReturn(booksPage);

        mockMvc.perform(get("/api/v1/books/by-author?author=Nikita Ivakin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].author", is("Nikita Ivakin")))
                .andExpect(jsonPath("$.content[0].title", is("Test Book")));
    }
}
