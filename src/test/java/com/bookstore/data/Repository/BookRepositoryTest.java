package com.bookstore.data.Repository;

import com.bookstore.data.model.Book;
import com.bookstore.data.model.Store;
import com.bookstore.web.exceptions.BookDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Sql(scripts = {"classpath:db/insert.sql"})
@Slf4j
@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;


    @Autowired
    StoreRepository storeRepository;

    Book book;



    @BeforeEach
    void setUp() {
        book = new Book();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void saveBookWithStoreObjectTest(){
        Optional<Store> optionalStore = storeRepository.findById(20);
        Store store = optionalStore.get();
        assertNotNull(store);

        book.setTitle("TLAMALAW");
        book.setAuthor("Oll John");
        book.setGenre("Fantasy");
        book.setYear(2019);
        book.setStore(store);

        assertDoesNotThrow(() -> bookRepository.saveBook(book));
        log.info("store and book info after saving --> {}", book.getStore());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void saveBookWithoutStoreObjectTest() {

        book.setTitle("The Kick");
        book.setAuthor("Young Matt");
        book.setGenre("Fantasy");
        book.setYear(2019);
        try {
            bookRepository.saveBook(book);
        } catch (BookDoesNotExistException e) {
            log.info("Book does not exist exception was thrown --> {}", e.getMessage());
        }
        assertThat(book.getId()).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void mapBookToAStoreTest() throws BookDoesNotExistException {
        Optional<Store> optionalStore = storeRepository.findById(20);
        Store store = optionalStore.get();
        assertNotNull(store);

        book.setTitle("The Drummer Boy");
        book.setAuthor("Cyprian Ekwensi");
        book.setGenre("Fiction");
        book.setYear(1960);
        book.setStore(store);

        log.info("book instance before adding --> {}", book);

        bookRepository.saveBook(book);

        log.info("Book instance after adding --> {}", book);
        log.info("Store instance after adding -- {}", store);

        assertThat(book.getId()).isNotNull();
        assertThat(store.getId()).isNotNull();
        assertThat(book.getStore()).isNotNull();
    }

    @Test
    void getListOfBooksAddedToAStoreTest(){

        Store store = new Store();
        store.setName("Macmillian");
        store.setContactNo("080887766");
        store.setLocation("Abuja");

        Book book1 = new Book();
        book1.setTitle("Rich Dad Poor Dad");
        book1.setAuthor("Robert Kiyosaki");
        book1.setGenre("Self-Help");
        book1.setYear(1997);
        book1.setStore(store);

        Book book2 = new Book();
        book2.setTitle("The Drummer Boy");
        book2.setAuthor("Cyprian Ekwensi");
        book2.setGenre("Fiction");
        book2.setYear(1960);
        book2.setStore(store);

        log.info("book instances before adding --> {}", book1, book2);

        store.addBooks(book1);
        store.addBooks(book2);

       storeRepository.save(store);

       log.info("book instances after saving --> {}", book1, book2);

       assertThat(book1.getId()).isNotNull();
       assertThat(book2.getId()).isNotNull();
    }

    @Test
    void findAllBooksInAStoreTest(){
        List<Book> savedBooks = bookRepository.findAll();
        log.info("Book List --> {}", savedBooks);
        assertThat(savedBooks).isNotNull();
        assertThat(savedBooks.size()).isEqualTo(6);
    }

    @Test
    void findBookByIdTest(){
        Optional<Book> optionalBook = bookRepository.findById(6);
        book = optionalBook.get();
        assertNotNull(book);
        log.info("Book --> {}", book);
    }

    @Test
    public void updateBookDetailsTest(){
        Book book = bookRepository.findById(5).orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Chike and the river");

        book.setYear(1966);
        bookRepository.save(book);
        log.info("After updating Book object --> {} ", book);
        assertThat(book.getYear()).isEqualTo(1966);

        book = bookRepository.findById(4).orElse(null);
        assertThat(book.getTitle()).isEqualTo("Things Fall Apart");
        assertThat(book).isNotNull();
        book.setGenre("Myths");
        bookRepository.save(book);
        log.info("After updating Book object --> {}", book);
        assertThat(book.getGenre()).isEqualTo("Myths");
    }

    @Test
    public void deleteBookFromDataBaseTest(){

        boolean book = bookRepository.existsById(4);
        assertThat(book).isTrue();
        log.info("book -> {}", book);
        bookRepository.deleteById(4);
        assertThat(bookRepository.existsById(4)).isFalse();
    }


}