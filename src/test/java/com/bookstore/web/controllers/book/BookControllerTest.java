package com.bookstore.web.controllers.book;

import com.bookstore.data.model.Book;
import com.bookstore.data.model.Store;
import com.bookstore.services.book.BookService;
import com.bookstore.services.store.StoreService;
import com.bookstore.web.exceptions.BookDoesNotExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/insert.sql"})
@Slf4j
class BookControllerTest {

    @Autowired
    StoreService storeService;

    @Autowired
    BookService bookService;


    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper;

    Book book;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        book = new Book();
    }

    @Test
    void testThatWeCanCallTheCreateBookEndpoint() throws Exception, BookDoesNotExistException {
        Store store = storeService.findStoreById(40);

        book.setYear(2000);
        book.setGenre("Sci-Fi");
        book.setTitle("Silicon Valley");
        book.setAuthor("John Travolta");
        book.setStore(store);
        bookService.saveBook(book);

        this.mockMvc.perform(post("/book/create")
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(book)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();
    }


    @Test
    void testThatWeCanCallTheGetAllBooksEndpoint() throws Exception {
        this.mockMvc.perform(get("/book/all"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
    }

    @Test
    void testThatWeCanCallTheUpdateBookEndpoint() throws Exception {

        Store store = storeService.findStoreById(50);

        book.setStore(store);
        book.setTitle("Head First");
        book.setAuthor("Gang of Four");
        book.setGenre("Design Patterns");
        book.setId(5);

        this.mockMvc.perform(post("/book/update")
                .contentType("application/json")
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void TestThatWeCanCallTheFindABookByIdEndpoint() throws Exception {
        this.mockMvc.perform(get("/book/find/5"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
    }


    @Test
    @Transactional
    @Rollback(value = false)
    void testThatWeCanCallTheDeleteBookByIdEndpoint() throws Exception {
        this.mockMvc.perform(delete("/book/delete/6"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
    }









}