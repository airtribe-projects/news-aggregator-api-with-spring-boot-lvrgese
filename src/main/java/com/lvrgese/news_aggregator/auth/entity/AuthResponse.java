package com.lvrgese.news_aggregator.auth.entity;

public class AuthResponse {
    private Long id;
    private String name;
    private String username;
    private String token;

    public AuthResponse(){}

    public AuthResponse(Long id, String name, String username,String token) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
