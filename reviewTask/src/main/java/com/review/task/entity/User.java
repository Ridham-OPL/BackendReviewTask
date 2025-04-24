package com.review.task.entity;

import com.review.task.enums.Gender;
import com.review.task.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    private String profileImage;

    private String contactNumber;

    private String pinCode;


    @Enumerated(EnumType.STRING)
    private Role accessRole;

    public User(String name, String username, String password, Date dob, Gender gender, String address, String contactNumber, String pinCode, Role accessRole) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.contactNumber = contactNumber;
        this.pinCode = pinCode;
        this.accessRole = accessRole;
    }

}
