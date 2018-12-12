package com.khn.mydbapp.models;

public class LoginResponse {
    private int status;
    private String msg, email, fullname, createdAt;

    public LoginResponse(int status, String msg, String email, String fullname, String createdAt) {
        this.status=status;
        this.msg=msg;
        this.fullname = fullname;
        this.email = email;
        this.createdAt= createdAt;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
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
}
