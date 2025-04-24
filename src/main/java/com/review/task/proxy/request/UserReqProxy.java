package com.review.task.proxy.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReqProxy {

    @NotBlank(message = "Please enter userName!")
    private String username;

    @NotBlank(message = "Please enter Password!")
    private String password;

    @NotBlank(message = "Please enter Name!")
    private String name;

    @NotBlank(message = "Please enter Date of Birth!")
    private Date dob;

    @NotBlank(message = "Please enter Gender!")
    private String gender;

    @NotBlank(message = "Please enter Address!")
    private String address;

    @NotBlank(message = "Please enter Contact Number!")
    private String contactNumber;

    @NotBlank(message = "Please enter PinCode!")
    private String pinCode;

    @NotBlank(message = "Please enter Role!")
    private String accessRole;
}
