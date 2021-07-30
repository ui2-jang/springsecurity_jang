package com.example.test.controllers;

import com.example.test.entity.User;
import com.example.test.form.UserForm;
import com.example.test.repository.UserRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.example.test.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
//    static ApplicationContext applicationContext = null;
//    @Autowired
//    UserController userController;
//
//    private ObjectMapper mapper;
//
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    private UserForm createUser(String username, String password) {
        UserForm userForm = new UserForm();
        userForm.setUsername(username);
        userForm.setPassword(password);
        return userForm;
    }

    // 첫 화면 LoginPage
    @Test
    public void  test001() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    // Signup Page
    @Test
    public void  test002() throws Exception{
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    //  login
    @Test
    void test_user_access_main() throws  Exception {
        mockMvc.perform(get("/main").with(user("ye").password("1").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(authenticated());

        mockMvc.perform(get("/main").with(user("jang").password("1").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(authenticated());
    }

    //  회원가입
    @Test
    void test_user_access_console() throws  Exception {
        String username = "tester@teseter.com";
        String password = "123123";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("ROLE_USER");

        userRepository.save(user);

        mockMvc.perform(get("/main").with(user(user.getUsername()).password(user.getPassword())))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }
}
