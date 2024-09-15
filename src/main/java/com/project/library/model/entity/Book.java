package com.project.library.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
@Table(name = "books")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    //genre must not be null.
    private String genre;

    @Column(unique = true)
    @Pattern(regexp = ".{13}", message = "ISBN must consist of exactly 13 characters")
    private String ISBN;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            title = "Unknown";
        }
        this.title = title;
    }

    public void setAuthor(String author) {
        if (author == null || author.isEmpty()) {
            author = "Unknown";
        }
        this.author = author;
    }

    public void setPublicationYear(LocalDate publicationYear) {
        if (publicationYear != null && publicationYear.isBefore(LocalDate.now())) {
            this.publicationYear = publicationYear;
        } else {
            this.publicationYear = null;
        }
    }

    public void setGenre(String genre) {
        if (genre == null || genre.isEmpty()) {
            genre = "Unknown";
        }
        this.genre = genre;
    }

    public void setISBN(String ISBN) {
        if (ISBN == null || ISBN.isEmpty() || ISBN.length() < 13) {
            ISBN = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 13);
        }
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

    public String getISBN() {
        return ISBN;
    }
}
