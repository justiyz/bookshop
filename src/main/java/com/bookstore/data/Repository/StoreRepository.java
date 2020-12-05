package com.bookstore.data.Repository;

import com.bookstore.data.model.Store;
import com.bookstore.web.exceptions.StoreDoesNotExistException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    default Store saveStore(Store store) throws StoreDoesNotExistException {
        if (isValid(store)){
            save(store);
        }
        return store;

    }

    default boolean isValid(Store store) throws StoreDoesNotExistException {
        if (!storeHasName(store)){
            throw new StoreDoesNotExistException("name can not be null");
        } if (!storeHasContactNo(store)){
            throw new StoreDoesNotExistException("contact can not be null");
        } if (!storeHasLocation(store)){
            throw new StoreDoesNotExistException("location can not be null");
        }
        return true;
    }

    default boolean storeHasName(Store store){
        if (store.getName() == null){
            return false;
        }
        return true;
    }

    default boolean storeHasContactNo(Store store){
        if (store.getContactNo() == null){
            return false;
        }
        return true;
    }

    default boolean storeHasLocation(Store store){
        if (store.getLocation() == null){
            return false;
        }
        return true;
    }


}
