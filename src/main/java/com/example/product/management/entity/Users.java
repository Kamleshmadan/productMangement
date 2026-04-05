package com.example.product.management.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    public Users() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String userName;

    String password;

    String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
