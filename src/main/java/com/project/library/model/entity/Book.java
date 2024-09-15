package com.project.library.model.entity;

import jakarta.persistence.*;
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

    //Додати валідацію та ексепшини
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String author;
    private LocalDate publicationYear;
    private String genre;
    @Column(unique = true)
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
