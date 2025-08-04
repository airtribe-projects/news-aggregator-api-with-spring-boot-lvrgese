package com.lvrgese.news_aggregator.auth.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public RegisterRequest(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public RegisterRequest() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
