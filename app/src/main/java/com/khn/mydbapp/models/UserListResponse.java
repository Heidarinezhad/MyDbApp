package com.khn.mydbapp.models;

public class UserListResponse {

    private String email, username, fullname, createdAt;

    public UserListResponse(String username, String email, String fullname, String createdAt) {
        this.username=username;
        this.fullname = fullname;
        this.email = email;
        this.createdAt= createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
