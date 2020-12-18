package com.bookstore.web.controllers.book;


import com.bookstore.data.model.Book;
import com.bookstore.services.book.BookService;
import com.bookstore.web.exceptions.BookDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<?> saveBook(@RequestBody Book book) throws BookDoesNotExistException {
        bookService.saveBook(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public  ResponseEntity<?> getAllBooks(){
        List<Book> bookList = bookService.findAllBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateBook(@RequestBody Book book) throws BookDoesNotExistException {
        log.info("book -> {}", book);
        bookService.updateBook(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findBookById(@PathVariable Integer id) throws BookDoesNotExistException {
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Integer id){
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
