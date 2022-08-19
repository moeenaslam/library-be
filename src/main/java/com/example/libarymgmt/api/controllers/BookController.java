package com.example.libarymgmt.api.controllers;

import com.example.libarymgmt.api.requests.CreateBookRequest;
import com.example.libarymgmt.api.requests.CreateLoanRequest;
import com.example.libarymgmt.api.requests.ReturnBookRequest;
import com.example.libarymgmt.api.responses.DefaultResponse;
import com.example.libarymgmt.book.Book;
import com.example.libarymgmt.book.BookService;
import com.example.libarymgmt.exceptions.BookNotAvailableException;
import com.example.libarymgmt.loan.LoanDto;
import com.example.libarymgmt.loan.LoanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "books")
public class BookController extends BaseController {

  static final Logger LOGGER = LogManager.getLogger(BookController.class);

  private final BookService bookService;
  private final LoanService loanService;

  public BookController(BookService bookService,
                        LoanService loanService) {
    this.bookService = bookService;
    this.loanService = loanService;
  }

  @RequestMapping(method = RequestMethod.POST, value = "")
  public ResponseEntity<DefaultResponse> addBook(@RequestBody @Valid CreateBookRequest request){
    try{
      return new ResponseEntity<>(bookService.createBook(request), HttpStatus.CREATED);
    } catch (Exception e){
      return new ResponseEntity<>(new DefaultResponse(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
  public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("id") Long id){
    try{
      return new ResponseEntity<>(bookService.updateBook(book, id), HttpStatus.OK);
    } catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  public ResponseEntity<Book> getBook(@PathVariable("id") Long id){
    try{
      return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    } catch (EntityNotFoundException e){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "", params = {"page","limit","searchQuery"})
  public ResponseEntity<Page<Book>> getBooks(@RequestParam("page") int page,
                                            @RequestParam("limit") int limit,
                                            @RequestParam("searchQuery") String searchQuery){
    try{
      return new ResponseEntity<>(bookService.getPaginatedBooks(page,limit,searchQuery), HttpStatus.OK);
    } catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  public ResponseEntity<Boolean> deleteBook(@PathVariable("id") Long id){
    try{
      return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.OK);
    } catch (EntityNotFoundException e){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /*
    Book loan APIs
   */

  @RequestMapping(method = RequestMethod.POST, value = "/{bookId}/loans")
  public ResponseEntity<?> loanBook(@RequestBody @Valid CreateLoanRequest loan, @PathVariable("bookId") Long bookId){
    try{
      return new ResponseEntity<>(loanService.loanBook(loan,bookId),HttpStatus.CREATED);
    } catch (BookNotAvailableException e) {
      return new ResponseEntity<>(new DefaultResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
    } catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @RequestMapping(method = RequestMethod.GET, value = "/{bookId}/loans")
  public ResponseEntity<List<LoanDto>> getLoanHistoryByBook(@PathVariable("bookId") Long bookId){
    try{
      return new ResponseEntity<>(loanService.getLoanHistory(bookId),HttpStatus.OK);
    } catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(method = RequestMethod.PATCH, value = "/loans/{loanId}")
  public ResponseEntity<?> returnBook(@RequestBody @Valid ReturnBookRequest request, @PathVariable("loanId") Long loanId){
    try{
      return new ResponseEntity<>(loanService.returnBook(request,loanId),HttpStatus.OK);
    } catch (Exception e){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
