package com.example.playlist_api.model;

import java.security.Identity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUser {
    
    @Id
    @GeneratedValue(strategy =  generationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    public AppUser() {}

    public AppUser(String username, String password, String role){
        this.username = username;
        this.pasword = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

}
