package com.lvrgese.news_aggregator.dto;

import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.entity.UserRole;

public class UserDTO {

    private Long userId;
    private String username;
    private String name;
    private UserRole userRole;
    private NewsPreferencesDTO newsPreferencesDTO;

    public UserDTO() {
    }

    public UserDTO(User user ,NewsPreferencesDTO pref){
        this.userId = user.getUserId();
        this.username =user.getUsername();
        this.name = user.getName();
        this.userRole = user.getUserRole();
        this.newsPreferencesDTO = pref;
    }

    public NewsPreferencesDTO getNewsPreferencesDTO() {
        return newsPreferencesDTO;
    }

    public void setNewsPreferencesDTO(NewsPreferencesDTO newsPreferencesDTO) {
        this.newsPreferencesDTO = newsPreferencesDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
