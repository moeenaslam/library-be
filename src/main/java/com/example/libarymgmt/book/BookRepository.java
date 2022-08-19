package com.example.libarymgmt.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends JpaRepository<Book, Long> {

  @Query("SELECT book FROM Book  book WHERE book.title LIKE %:searchQuery% OR book.genre LIKE %:searchQuery% " +
      "OR book.author.name LIKE %:searchQuery% OR book.publisher.name LIKE %:searchQuery% ")
  Page<Book> findBooksBySearchAndPage(Pageable page, @Param("searchQuery") String searchQuery);
}
