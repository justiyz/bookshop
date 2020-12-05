package com.bookstore.services.book;

import com.bookstore.data.Repository.BookRepository;
import com.bookstore.data.model.Book;
import com.bookstore.web.exceptions.BookDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"classpath:db/insert.sql"})
@Slf4j
class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService = new BookServiceImpl();

    @Autowired
    BookService bookServiceImpl;

    Book testBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testBook = new Book();
    }

    @Test
    public void mockTheSaveBookToRepositoryTest() throws BookDoesNotExistException {
        when(bookRepository.save(testBook)).thenReturn(testBook);
        bookService.saveBook(testBook);

        log.info("Instance after saving --> {}", testBook);

        verify(bookRepository, times(1)).save(testBook);
    }

    @Test
    public void mockTheFindBookByIdTest() throws BookDoesNotExistException {
        when(bookRepository.findById(8)).thenReturn(Optional.of(testBook));
        bookService.findBookById(8);

        log.info("Book Id --> {}", testBook);
        verify(bookRepository, times(1)).findById(8);
    }

    @Test
    void mockTheFindAllBooksTest(){
        when(bookRepository.findAll()).thenReturn(List.of(testBook));
        bookService.findAllBooks();

        log.info("List --> {}", testBook);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void mockTheDeleteBookByIdTest() throws BookDoesNotExistException {
        doNothing().when(bookRepository).deleteById(5);
        bookService.deleteBookById(5);

        log.info("Deleted --> {}", testBook);
        verify(bookRepository, times(1)).deleteById(5);
    }

    @Test
    void whenBookDoesNotExist_thenThrowExceptionTest(){
        assertThrows(BookDoesNotExistException.class, ()-> bookService.findBookById(56));
    }
}