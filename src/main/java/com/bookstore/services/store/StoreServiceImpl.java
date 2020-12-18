package com.bookstore.services.store;

import com.bookstore.data.Repository.StoreRepository;
import com.bookstore.data.model.Store;
import com.bookstore.web.exceptions.StoreDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store findStoreById(Integer id) throws StoreDoesNotExistException {
        Optional<Store> optionalStore = storeRepository.findById(id);
        Store store = optionalStore.get();
        if (store == null) {
            throw new StoreDoesNotExistException("Store does not exist");
        }
        return store;
    }

    @Override
    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store updateStore(Store store) throws StoreDoesNotExistException {
        confirmStoreBeforeUpdating(store);
        return store;
    }

    public Boolean confirmStoreBeforeUpdating(Store store) throws StoreDoesNotExistException {
        Optional<Store> optionalStore = storeRepository.findById(store.getId());
        Store existingStore = optionalStore.get();

        if (existingStore == null){
            throw new StoreDoesNotExistException("store with the id" + store.getId()+ "does not exist");
        }
        if (store.getContactNo() != null){
            existingStore.setContactNo(store.getLocation());
        }
        if (store.getLocation() != null){
            existingStore.setLocation(store.getLocation());
        }
        if (store.getName() != null){
            existingStore.setName(store.getName());
        }
        storeRepository.save(existingStore);
        return true;
    }

    @Override
    public void deleteStoreById(Integer id) {
        storeRepository.deleteById(id);
    }
}
