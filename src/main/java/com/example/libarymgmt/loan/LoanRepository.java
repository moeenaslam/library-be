package com.example.libarymgmt.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {

  @Query("SELECT loan FROM Loan loan WHERE loan.book.id=:bookId AND loan.returnedOn IS NULL")
  Loan getActiveLoanByBook(@Param("bookId") Long bookId);

  @Query("SELECT new com.example.libarymgmt.loan.LoanDto(loan.loanedAt,loan.dueDate,loan.returnedOn, loan.fee,loan.loanedToMember.name, loan.id) FROM Loan  loan " +
      "WHERE loan.book.id=:bookId ORDER BY loan.loanedAt DESC")
  List<LoanDto> getLoansByBook_IdOrderByLoanedAtDesc(Long bookId);
}
