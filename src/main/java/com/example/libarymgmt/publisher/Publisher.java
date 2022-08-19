package com.example.libarymgmt.publisher;

import com.example.libarymgmt.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_publishers")
@Getter
@Setter
@NoArgsConstructor
public class Publisher {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;
  private String country;

  @JsonIgnore
  @OneToMany(mappedBy = "publisher")
  private Set<Book> books = new HashSet<>();

}
