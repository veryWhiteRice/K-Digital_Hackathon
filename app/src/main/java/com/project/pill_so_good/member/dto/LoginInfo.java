package com.project.pill_so_good.member.dto;

public class LoginInfo {

    private final String email;
    private final String password;

    public LoginInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean areFieldsNotEmpty() {
        return (email != null && !email.isEmpty()) && (password != null && !password.isEmpty());
    }
}
