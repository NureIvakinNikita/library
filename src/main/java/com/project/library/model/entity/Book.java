package com.project.library.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.library.exception.BookArgumentsViolationException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.project.library.exception.ExceptionMessages.BOOK_DATE_CANT_BE_IN_FUTURE;

@Entity
@Table(name = "TBL_BOOK")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    //Додати ексепшини
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Title must not be null.")
    @NotEmpty(message = "Title must not be empty.")
    private String title;

    @NotNull(message = "Author must not be null.")
    @NotEmpty(message = "Author must not be empty.")
    private String author;

    @PastOrPresent(message = "The year of publication cannot be greater than the current year.")
    private LocalDate publicationYear;

    @NotNull(message = "Genre must not be null.")
    @NotEmpty(message = "Genre must not be empty.")
    private String genre;

    @Column(unique = true)
    @NotNull(message = "ISBN must not be null.")
    @NotEmpty(message = "ISBN must not be empty.")
    @Pattern(regexp = "\\d{13}", message = "ISBN must consist of exactly 13 characters")
    private String ISBN;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(LocalDate publicationYear) {
        /*if (publicationYear != null && publicationYear.isBefore(LocalDate.now())) {
            this.publicationYear = publicationYear;
        } else {
            throw new BookArgumentsViolationException(BOOK_DATE_CANT_BE_IN_FUTURE.getText());
        }*/
        this.publicationYear = publicationYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {

        this.ISBN = ISBN;
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublicationYear() {
        return publicationYear;
    }

    public String getGenre() {
        return genre;
    }

}
