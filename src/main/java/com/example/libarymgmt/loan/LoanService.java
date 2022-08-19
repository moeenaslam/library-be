package com.example.libarymgmt.loan;

import com.example.libarymgmt.api.requests.CreateLoanRequest;
import com.example.libarymgmt.api.requests.ReturnBookRequest;
import com.example.libarymgmt.book.BookService;
import com.example.libarymgmt.exceptions.BookNotAvailableException;
import com.example.libarymgmt.member.MemberRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

  static final Logger LOGGER = LogManager.getLogger(LoanService.class);
  private final LoanRepository loanRepository;
  private final MemberRepository memberRepository;
  private final BookService bookService;

  public LoanService(LoanRepository loanRepository,
                     MemberRepository memberRepository, BookService bookService) {
    this.loanRepository = loanRepository;
    this.memberRepository = memberRepository;
    this.bookService = bookService;
  }

  public LoanDto loanBook(CreateLoanRequest request, Long bookId) throws BookNotAvailableException {
    if(!this.getBookAvailability(bookId)){
      //book is not available to loan
      throw new BookNotAvailableException("Book is not available for loan, it is already loaned");
    }
    try{
      Loan loan = new Loan(request);
      if( request.getLoanedToMember().getId()!=null){
        //already a member, link
        loan.setLoanedToMember(memberRepository.getReferenceById(request.getLoanedToMember().getId()));
      }
      else{
        loan.setLoanedToMember(request.getLoanedToMember());
      }
      loan.setBook(bookService.getBook(bookId));
      loanRepository.save(loan);
      return new LoanDto(loan);
    }catch (Exception e){
      LOGGER.error("Error",e);
      throw e;
    }
  }

  private boolean getBookAvailability(Long bookId) {
    Loan alreadyLoan = loanRepository.getActiveLoanByBook(bookId);
    return alreadyLoan == null;
  }

  public List<LoanDto> getLoanHistory(Long bookId) {
    return loanRepository.getLoansByBook_IdOrderByLoanedAtDesc(bookId);
  }

  public Object returnBook(ReturnBookRequest request, Long loanId) {
    Loan loan = loanRepository.findById(loanId).orElseThrow();
    loan.setReturnedOn(request.getReturnedOn());
    loanRepository.save(loan);
    return new LoanDto(loan);
  }
}
