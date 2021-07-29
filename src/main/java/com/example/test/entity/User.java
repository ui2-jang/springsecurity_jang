package com.example.test.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userid;
    @Column(name="user_name")
    private String username;
    @Column(name="user_password")
    private String password;
    private String role;
}
