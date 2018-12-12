package com.khn.mydbapp.models;

public class User {
    private String username, email, fullname, createdAt;

    public User(String username, String email, String fullname, String createdAt) {
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
