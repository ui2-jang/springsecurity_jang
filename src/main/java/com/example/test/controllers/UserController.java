package com.example.test.controllers;

import com.example.test.entity.User;
import com.example.test.form.UserForm;
import com.example.test.service.UserDetailObj;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ModelAttribute
    public UserForm setUpForm() {
        return new UserForm();
    }


    @PostMapping("/signup_")
    public String signup(UserForm userForm, @RequestParam("confirm_password") String confirm) {
        if(userForm.getPassword().equals(confirm)) {
            Authentication auth = userService.joinUser(userForm);
            SecurityContextHolder.getContext().setAuthentication(auth);
//            UserDetailObj userDetailObj = new UserDetailObj(user);
//            userService.loadUserByUsername(user.getUsername());

            return "redirect:/main";
        } else {
            return "signup";
        }
    }
}
