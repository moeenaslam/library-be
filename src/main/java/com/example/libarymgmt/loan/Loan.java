package com.example.libarymgmt.loan;

import com.example.libarymgmt.api.requests.CreateLoanRequest;
import com.example.libarymgmt.book.Book;
import com.example.libarymgmt.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "t_loans")
@Getter
@Setter
@NoArgsConstructor
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDate loanedAt;
  @Column(nullable = false)
  private LocalDate dueDate;
  private Date returnedOn;

  private BigDecimal fee;

  @ManyToOne
  private Book book;

  @ManyToOne(cascade = {CascadeType.PERSIST})
  private Member loanedToMember;

  public Loan(CreateLoanRequest request) {
    this.loanedAt = LocalDate.now();
    this.dueDate = request.getDueDate();
    this.fee = request.getFee();
  }
}
