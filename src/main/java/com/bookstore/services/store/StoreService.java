package com.bookstore.services.store;

import com.bookstore.data.model.Store;
import com.bookstore.web.exceptions.StoreDoesNotExistException;

import java.util.List;

public interface StoreService {

        Store saveStore(Store store);
        Store findStoreById(Integer id) throws StoreDoesNotExistException;
        List<Store> findAllStores();
        Store updateStore(Store store) throws StoreDoesNotExistException;
        void deleteStoreById(Integer id);

}
