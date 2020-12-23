package com.bookstore.services.book;

import com.bookstore.data.Repository.BookRepository;
import com.bookstore.data.Repository.StoreRepository;
import com.bookstore.data.model.Book;
import com.bookstore.web.exceptions.BookDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Book saveBook(Book book) throws BookDoesNotExistException {
        if(book == null){
            throw new BookDoesNotExistException("Book does not exist exception was thrown");
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) throws BookDoesNotExistException {
        confirmBookBeforeUpdating(book);
        return book;
    }

    public boolean confirmBookBeforeUpdating(Book book) throws BookDoesNotExistException {
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        Book existingBook = optionalBook.get();

        if (existingBook == null) {
            throw new BookDoesNotExistException("Book with id:" + book.getId() + "does not exist");
        }
        if (book.getAuthor() != null) {
            existingBook.setAuthor(book.getAuthor());
        }
        if (book.getTitle() != null) {
            existingBook.setTitle(book.getTitle());
        }
        if (book.getGenre() != null) {
            existingBook.setGenre(book.getGenre());
        }
        if (book.getYear() != null) {
            existingBook.setYear(book.getYear());
        }
        if (book.getStore() != null) {
            log.info("store -> {}", book.getStore());
            existingBook.setStore(book.getStore());
        }
        bookRepository.saveBook(existingBook);
        return true;
    }

    @Override
    public Book findBookById(Integer id) throws BookDoesNotExistException {
        Book book = bookRepository.findById(id).orElse(null);
        if(book != null){
            return book;
        } else{
            throw new BookDoesNotExistException("Book with the Id:"+id+ "does not exist...");
        }
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBookById(Integer id)  {
        bookRepository.deleteById(id);
    }
}
