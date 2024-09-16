package com.project.library.repository;

import com.project.library.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> getBookById(long id);

    Page<Book> getBookByAuthor(String author, Pageable pageable);

    Optional<Book> getBookByTitle(String title);

    Page<Book> getBookByGenre(String genre, Pageable pageable);

    void deleteBookById(long id);

}
