package com.bookstore.data.Repository;

import com.bookstore.data.model.Book;
import com.bookstore.data.model.Store;
import com.bookstore.web.exceptions.BookDoesNotExistException;
import com.bookstore.web.exceptions.StoreDoesNotExistException;
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

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    BookRepository bookRepository;

    Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
    }


    @Test
    void testThatWeCanSaveAStore(){
        store.setLocation("Greater Accra");
        store.setContactNo("090911226677");
        store.setName("The Divine Bookstore");
        try {
            storeRepository.saveStore(store);
        } catch (StoreDoesNotExistException ex){
            ex.printStackTrace();
        }
        assertThat(store.getId()).isNotNull();
        log.info("newly saved store --> {}", store);
    }

    @Test
    void testThatStoreShouldNotBeSavedWithoutLocation(){
        store.setContactNo("090911226677");
        store.setName("The Divine Bookstore");
        try {
            storeRepository.saveStore(store);
        } catch (StoreDoesNotExistException ex){
            ex.printStackTrace();
        }
        assertThat(store.getId()).isNull();
        log.info("saved store --> {}", store);

    }

    @Test
    void testThatStoreShouldNotBeSavedWithoutContact(){
        store.setLocation("Greater Accra");
        store.setName("The Divine Bookstore");

        assertThrows( StoreDoesNotExistException.class, () -> storeRepository.saveStore(store));
        assertThat(store.getId()).isNull();
        log.info("saved store --> {}", store);
    }

    @Test
    void testThatWeCanFindStoreById(){
        Optional<Store> optionalStore = storeRepository.findById(30);
        Store store = optionalStore.get();
        assertNotNull(store.getId());
        log.info("store --> {}", store);
    }

    @Test
    void testThatWeCanFindAllStores(){
        List<Store> stores = storeRepository.findAll();
        assertNotNull(stores);
        log.info("all stores --> {}", stores);
    }

    @Test
    void testThatWeCanUpdateStore(){
        Optional<Store> optionalStore = storeRepository.findById(40);
        store = optionalStore.get();
        store.setContactNo("123-456-345-56");
        store.setLocation("Madagascar");

        assertDoesNotThrow(() -> storeRepository.saveStore(store));
        assertThat(store.getId()).isNotNull();
        log.info("updated store --> {}", store);
    }

    @Test
    void testThatWeCanDeleteStoreById(){
        Optional<Store> optionalStore = storeRepository.findById(20);
        store = optionalStore.get();
        assertNotNull(store);
        storeRepository.deleteById(20);
        assertFalse(storeRepository.existsById(20));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void saveAndFindAllTheBooksInAStoreTest() throws BookDoesNotExistException {
        Optional<Store> optionalStore = storeRepository.findById(10);
        store = optionalStore.get();
        assertNotNull(store);

        Book book = new Book();
        book.setAuthor("Bradley Cooper");
        book.setTitle("Maybe it's Time");
        book.setGenre("Fantasy");
        book.setYear(1900);
        book.setStore(store);
        bookRepository.saveBook(book);
        assertThat(book.getId()).isNotNull();

        Book book1 = new Book();
        book1.setAuthor("Richard Millie");
        book1.setTitle("Ghost");
        book1.setGenre("Horror");
        book1.setYear(2000);
        book1.setStore(store);
        bookRepository.saveBook(book1);
        assertThat(book1.getId()).isNotNull();

        assertDoesNotThrow( () -> storeRepository.saveStore(store));
        assertThat(store.getBookList()).isNotEmpty();
        log.info("store details --> {}", store);
    }



}