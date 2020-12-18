package com.bookstore.services.book;

import com.bookstore.data.model.Book;
import com.bookstore.web.exceptions.BookDoesNotExistException;

import java.util.List;

public interface BookService {

    Book saveBook(Book book) throws BookDoesNotExistException;
    Book updateBook(Book book) throws BookDoesNotExistException;
    Book findBookById(Integer id) throws BookDoesNotExistException;
    List<Book> findAllBooks();
    void deleteBookById(Integer id) ;
}
