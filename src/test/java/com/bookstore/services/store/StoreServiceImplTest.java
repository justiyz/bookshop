package com.bookstore.services.store;

import com.bookstore.data.Repository.StoreRepository;
import com.bookstore.data.model.Store;
import com.bookstore.web.exceptions.StoreDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class StoreServiceImplTest {

    @Mock
    StoreRepository storeRepository;

    @InjectMocks
    StoreService storeService = new StoreServiceImpl();

    Store store;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        store = new Store();
    }

    @Test
    void mockTheSaveStoreRepositoryTest(){
        when(storeRepository.save(store)).thenReturn(store);
        storeService.saveStore(store);
        verify(storeRepository, times(1)).save(store);
    }

    @Test
    void mockTheFindStoreByIdRepositoryTest(){
        when(storeRepository.findById(20)).thenReturn(Optional.of(store));
        assertDoesNotThrow(() -> storeService.findStoreById(20));
        verify(storeRepository, times(1)).findById(20);
    }

    @Test
    void mockTheFindAllStoresRepositoryTest(){
        when(storeRepository.findAll()).thenReturn(List.of(store));
        storeService.findAllStores();
        verify(storeRepository, times(1)).findAll();
    }

    @Test
    void mockTheUpdateStoreRepositoryTest(){
        when(storeRepository.save(store)).thenReturn(store);
        store.setName("MockName");
        storeService.saveStore(store);
        verify(storeRepository, times(1)).save(store);
    }

    @Test
    void mockTheDeleteStoreRepositoryTest(){
        doNothing().when(storeRepository).deleteById(6);
        storeService.deleteStoreById(6);
        verify(storeRepository, times(1)).deleteById(6);
    }





}