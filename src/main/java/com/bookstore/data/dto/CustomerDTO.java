package com.bookstore.data.dto;

import com.bookstore.data.model.Gender;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Data
@Component
public class CustomerDTO {

    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String password;
    private Gender gender;
}
