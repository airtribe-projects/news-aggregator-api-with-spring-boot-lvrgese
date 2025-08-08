package com.lvrgese.news_aggregator.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotNull
    private UserRole userRole;

    @NotNull
    private Boolean isEnabled;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private NewsPreferences newsPreferences;

    public User() {
    }

    public User(builder builder) {
        this.userRole = builder.userRole;
        this.password = builder.password;
        this.name = builder.name;
        this.username = builder.username;
        this.isEnabled = builder.isEnabled;
    }

    public static class builder{
        private String username;
        private String name;
        private String password;
        private UserRole userRole;
        private Boolean isEnabled;

        public builder username(String username) {
            this.username= username;
            return this;
        }

        public builder name(String name) {
            this.name= name;
            return this;
        }
        public builder password(String password) {
            this.password= password;
            return this;
        }
        public builder userRole(UserRole role) {
            this.userRole= role;
            return this;
        }
        public builder isEnabled(Boolean val) {
            this.isEnabled= val;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public NewsPreferences getNewsPreferences() {
        return newsPreferences;
    }

    public void setNewsPreferences(NewsPreferences newsPreferences) {
        this.newsPreferences = newsPreferences;
    }
}
