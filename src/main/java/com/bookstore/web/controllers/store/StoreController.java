package com.bookstore.web.controllers.store;



import com.bookstore.data.model.Store;
import com.bookstore.services.store.StoreService;
import com.bookstore.web.exceptions.StoreDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreService storeService;


    @PostMapping("/create")
    public ResponseEntity<?> saveStore(@RequestBody Store store) throws StoreDoesNotExistException {
        storeService.saveStore(store);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllStores() {
        List<Store> storeList = storeService.findAllStores();
        return new ResponseEntity<>(storeList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findStoreById(@PathVariable Integer id) throws StoreDoesNotExistException {
        Store store = storeService.findStoreById(id);
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateStore(@RequestBody Store store) throws StoreDoesNotExistException {
        storeService.updateStore(store);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable Integer id){
        storeService.deleteStoreById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
