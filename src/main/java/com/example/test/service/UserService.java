package com.example.test.service;

import com.example.test.entity.User;
import com.example.test.form.UserForm;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService implements  UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession session;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Authentication joinUser(UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());

        String rawPassword = userForm.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loadUserByUsername(user.getUsername()).getUsername(),
                        null,loadUserByUsername(user.getUsername()).getAuthorities());
        return authenticationToken;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        session.setAttribute("user", user.getUsername());
        return new UserDetailObj(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
