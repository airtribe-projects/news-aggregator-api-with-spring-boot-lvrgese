package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final NewsPreferencesService newsPreferencesService;

    public UserService(UserRepository userRepository, NewsPreferencesService newsPreferencesService) {
        this.userRepository = userRepository;
        this.newsPreferencesService = newsPreferencesService;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO getUserProfile() {
        User user = getCurrentUser();
        NewsPreferencesDTO pref = null;
        try {
            pref = newsPreferencesService.getNewsPreferencesForCurrentUser();
        }
        catch (Exception ignored){}

        return new UserDTO(user,pref);
    }
}
