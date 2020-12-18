package com.bookstore.web.controllers.store;

import com.bookstore.data.model.Book;
import com.bookstore.data.model.Store;
import com.bookstore.services.book.BookService;
import com.bookstore.services.store.StoreService;
import com.bookstore.web.exceptions.StoreDoesNotExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/insert.sql"})
class StoreControllerTest {

    @Autowired
    BookService bookService;

    @Autowired
    StoreService storeService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper;

    Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
        mapper = new ObjectMapper();
    }


    @Test
    void testThatWeCanCallTheCreateStoreEndpoint() throws Exception {
        store.setName("New Store");
        store.setContactNo("09099887766");
        store.setLocation("Abidjan");
        storeService.saveStore(store);

        this.mockMvc.perform(post("/store/create")
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(store)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();
    }

    @Test
    void testThatWeCanCallTheFindAllStoresEndpoint() throws Exception {
        this.mockMvc.perform(get("/store/all"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
    }

    @Test
    void testThatWeCanCallTheFindStoreByIdEndpoint() throws Exception {
        this.mockMvc.perform(get("/store/find/40"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
    }

    @Test
    void testThatWeCanCallTheUpdateStoreEndpoint() throws Exception {
        store.setId(10);;
        store.setLocation("Cohort4");
        store.setContactNo("0000000000000");

        this.mockMvc.perform(post("/store/update")
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(store)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

    }
}