package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.exception.PreferencesNotFoundException;
import com.lvrgese.news_aggregator.exception.UserNotFoundException;
import com.lvrgese.news_aggregator.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final NewsService newsService;


    public UserService(UserRepository userRepository, NewsService newsService) {
        this.userRepository = userRepository;
        this.newsService = newsService;
    }

    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id "+ id + " is not found"));
        NewsPreferencesDTO pref = null;
        try {
            pref = newsService.getNewsPreferenceByUserId(id);
        }
        catch (Exception ignored){}

        return new UserDTO(user,pref);
    }
}
