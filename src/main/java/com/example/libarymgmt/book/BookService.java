package com.example.libarymgmt.book;

import com.example.libarymgmt.api.requests.CreateBookRequest;
import com.example.libarymgmt.api.responses.DefaultResponse;
import com.example.libarymgmt.author.AuthorRepository;
import com.example.libarymgmt.loan.LoanRepository;
import com.example.libarymgmt.publisher.PublisherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BookService {

  static final Logger LOGGER = LogManager.getLogger(BookService.class);

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private AuthorRepository authorRepository;
  @Autowired
  private PublisherRepository publisherRepository;
  @Autowired
  private LoanRepository loanRepository;

  public DefaultResponse createBook(CreateBookRequest request)  {
    try{
      Book book = new Book(request);
      persistBook(book);
      return new DefaultResponse(true,"Successfully created book");
    } catch (Exception e){
      String errorMessage = "Unexpected error occurred while creating book";
      LOGGER.error(errorMessage,e);
      throw e;
    }
  }

  public Book updateBook(Book book, Long id){
    try{
      Book existingBook = bookRepository.findById(id).orElseThrow();
      existingBook.update(book);
      bookRepository.save(existingBook);
      return existingBook;
    }catch (Exception e){
      String errorMessage = "Unexpected error occurred while updating book";
      LOGGER.error(errorMessage,e);
      throw e;
    }
  }

  private void persistBook(Book book) {
    if(book.getAuthor()!=null && book.getAuthor().getId()!=null){
      book.setAuthor(authorRepository.getReferenceById(book.getAuthor().getId()));
    }
    if(book.getPublisher()!=null && book.getPublisher().getId()!=null){
      book.setPublisher(publisherRepository.getReferenceById(book.getPublisher().getId()));
    }
    bookRepository.save(book);
  }

  public Book getBook(Long id) {
    try{
      Book book =  bookRepository.findById(id).orElseThrow();
      book.setAvailableToLoan(loanRepository.getActiveLoanByBook(id)==null);
      return book;
    }catch (EntityNotFoundException e){
      LOGGER.error("No book found with id"+id,e);
      throw e;
    }
  }

  public Page<Book> getPaginatedBooks(int page, int limit, String searchQuery) {
    try{
      Pageable pageable = PageRequest.of(page,limit);
      return bookRepository.findBooksBySearchAndPage(pageable,searchQuery);
    }catch (Exception e){
      LOGGER.error("Unexpected error occurred while getting book page",e);
      throw e;
    }

  }

  @Transactional
  public boolean deleteBook(Long id) {
    Book book = bookRepository.findById(id).orElseThrow();
    bookRepository.delete(book);
    return true;

  }
}
