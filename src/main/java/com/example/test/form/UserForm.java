package com.example.test.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
@Data
public class UserForm {
    private int userid;
    @NotEmpty
    @Email
    private String username;
    @NotEmpty
    @Min(6)
    private String password;
}
