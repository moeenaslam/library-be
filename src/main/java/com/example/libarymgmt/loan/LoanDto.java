package com.example.libarymgmt.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {
  private LocalDate loanedAt;
  private LocalDate dueDate;
  private Date returnedOn;
  private BigDecimal fee;
  private String loanedToMemberName;
  private Long id;

  public LoanDto(Loan loan) {
    this.loanedAt = loan.getLoanedAt();
    this.dueDate = loan.getDueDate();
    this.returnedOn = loan.getReturnedOn();
    this.fee = loan.getFee();
    this.loanedToMemberName = loan.getLoanedToMember().getName();
    this.id = loan.getId();
  }
}
