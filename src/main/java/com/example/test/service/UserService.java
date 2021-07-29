package com.example.test.service;

import com.example.test.entity.User;
import com.example.test.form.UserForm;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService implements  UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession session;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    public User joinUser(UserForm userForm) {
//        User user = new User();
//        user.setUsername(userForm.getUsername());
//
//        String rawPassword = userForm.getPassword();
//        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//
//        user.setPassword(encPassword);
//        user.setRole("ROLE_USER");
//        userRepository.save(user);
//        loadUserByUsername(user.getUsername());
//        return user;
//    }
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

    // security 설정에서 loginProcessingUrl("/login_")
    // /login_ 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는
    // loadUserByUsername 함수가 실행!!!
    // 타입을 꼭 지킬것
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // String username ==> form태그의 input name과 꼭 일치해야함
//        // 바꾸고싶다면? usernameParameter를 configure에 추가
//        User user = userRepository.findByUsername(username);
//        if(user!=null) {
//            return new UserDetailObj(user);
//            // return 되는 UserDetailObj는 Authentication(내부 UserDetails)
//            //  ==> Authentiaction(UserDetails)
//        }
//
//        return null;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        session.setAttribute("user", user.getUsername());
        System.out.println("%%%%%%%%%"+user);
        return new UserDetailObj(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
