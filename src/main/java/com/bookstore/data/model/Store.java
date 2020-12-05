package com.bookstore.data.model;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String location;

    private String contactNo;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> bookList;

    public void addBooks(Book book){
        if (bookList == null){
            bookList =new ArrayList<>();
        }
        bookList.add(book);
    }








}
