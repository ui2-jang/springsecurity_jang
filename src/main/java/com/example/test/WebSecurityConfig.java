package com.example.test;

import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    // 해당 메서드의 리턴되는 오브젝트를 Ioc로 등록해줌
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/", "/login", "/signup").permitAll()
//                .antMatchers("/main").authenticated()
//                .antMatchers("/h2-console/**").hasRole("ADMIN")
//        .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login_")
//                // /login_ 요청이 올경우 security가 대신해서 로그인을 진행
//                // detail 클레스를 따로 생성해야 하는 이유? => 로그인 진행을 완료하면 security session을 생성하는데 이것이 (Session ContextHolder)
//                // 그 세션에 벨류(객체)의 데이터 형태는 무조건 Authentication으로 정해져있음.
//                // Authentication 안에는 user의 정보가 있을 것
//                // User 오브젝트의 타입은? UserDetails 타입 객체
//                // 즉, Security Session => Authentication => UserDetails
//                .defaultSuccessUrl("/main")
//                .failureUrl("/login?error=true")
//        .and()
//            .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login");
//
//        http.headers().frameOptions().disable();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/signup","/signup_").permitAll()
                .antMatchers("/main").authenticated()
                .antMatchers("/h2-console/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_")
                .defaultSuccessUrl("/main")
                .failureUrl("/login?error=true")
        .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");


        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}
