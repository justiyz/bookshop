package com.bookstore.services.book;

import com.bookstore.data.Repository.BookRepository;
import com.bookstore.data.model.Book;
import com.bookstore.web.exceptions.BookDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) throws BookDoesNotExistException {
        if(book == null){
            throw new BookDoesNotExistException("Book does not exist exception was thrown");
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
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
