package com.example.test.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserForm {
    private int userid;
    @NotEmpty(message = "必須項目です。")
    @Email(message = "UserのIDはEmailの型で入力して下さい。")
    private String username;
    @NotEmpty(message = "必須項目です。")
//    @Min(6,message = "パスワードは最小6文字です。")
//    private String password;
    @Size(min=6,message = "パスワードは最小6文字です。")
    private String password;
}
