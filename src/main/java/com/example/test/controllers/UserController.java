package com.example.test.controllers;

import com.example.test.form.UserForm;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ModelAttribute
    public UserForm setUpForm() {
        return new UserForm();
    }

    @PostMapping("/signup_")
    public String signup(@Validated UserForm userForm,
                         BindingResult bindingResult,
                         @RequestParam("confirm_password") String confirm) {
            if(bindingResult.hasErrors()) {
                return "redirect:/signup";
            } else {
                if(userForm.getPassword().equals(confirm)) {
                    Authentication auth = userService.joinUser(userForm);
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    return "redirect:/main";
                }
            }

            return "redirect:/signup";
        }

}
