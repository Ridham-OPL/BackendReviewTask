package com.review.task.proxy.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResProxy {

    private Long id;

    private String username;

    private String password;

    private String name;

    private Date dob;

    private String gender;

    private String address;

    private String profileImage;

    private String contactNumber;

    private String pinCode;

    private String accessRole;
}