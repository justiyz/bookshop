package com.bookstore.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
//import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String author;

    private String genre;

    private Integer year;


    @ManyToOne()
    @ToString.Exclude
    @JsonIgnore
    private Store store;


}
