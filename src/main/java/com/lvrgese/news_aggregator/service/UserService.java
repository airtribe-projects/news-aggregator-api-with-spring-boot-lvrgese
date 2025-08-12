package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final NewsPreferencesService newsPreferencesService;


    public UserService(UserRepository userRepository, NewsPreferencesService newsPreferencesService) {
        this.userRepository = userRepository;
        this.newsPreferencesService = newsPreferencesService;
    }


}
