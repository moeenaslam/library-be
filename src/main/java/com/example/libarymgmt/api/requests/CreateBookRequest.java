package com.example.libarymgmt.api.requests;

import com.example.libarymgmt.author.Author;
import com.example.libarymgmt.publisher.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

  @NotBlank
  private String title;
  @NotBlank
  private String isbn;
  private String genre;
  private LocalDate publishedOn;

  private Author author;
  private Publisher publisher;
}
