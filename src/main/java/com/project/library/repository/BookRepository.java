package com.project.library.repository;

import com.project.library.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> getBookById(long id);

    Optional<Book> getBookByAuthor(String author);

    Optional<Book> getBookByTitle(String title);

    Optional<Book> getBookByGenre(String genre);

    void deleteBookById(long id);

}
