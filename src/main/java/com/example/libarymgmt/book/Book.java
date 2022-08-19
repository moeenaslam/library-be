package com.example.libarymgmt.book;

import com.example.libarymgmt.api.requests.CreateBookRequest;
import com.example.libarymgmt.author.Author;
import com.example.libarymgmt.loan.Loan;
import com.example.libarymgmt.publisher.Publisher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_books")
@Getter
@Setter
@NoArgsConstructor
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String title;
  @Column(nullable = false,unique = true)
  private String isbn;
  private String genre;
  private LocalDate publishedOn;

  @ManyToOne(cascade = {CascadeType.PERSIST})
  private Publisher publisher;

  @ManyToOne(cascade = {CascadeType.PERSIST})
  private Author author;


  @JsonIgnore
  @OneToMany(mappedBy = "book", cascade = {CascadeType.ALL})
  private Set<Loan> loans = new HashSet<>();

  @Transient
  private boolean availableToLoan = false;



  public Book(CreateBookRequest request){
    this.title = request.getTitle();
    this.isbn = request.getIsbn();
    this.genre = request.getGenre();
    this.publishedOn = request.getPublishedOn();
    this.author = request.getAuthor();
    this.publisher = request.getPublisher();
  }


  public void update(Book book) {
    this.title = book.title;
    this.isbn = book.isbn;
    this.genre = book.genre;
    this.publishedOn = book.publishedOn;
    this.publisher.setName(book.getPublisher().getName());
    this.author.setName(book.getAuthor().getName());
  }
}
