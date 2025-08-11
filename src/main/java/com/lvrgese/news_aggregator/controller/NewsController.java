package com.lvrgese.news_aggregator.controller;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.exception.PreferencesNotFoundException;
import com.lvrgese.news_aggregator.exception.UserNotFoundException;
import com.lvrgese.news_aggregator.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserById()  {
        return ResponseEntity.ok(newsService.getUserProfile());
    }

    @PostMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> createPreferencesForCurrentUser(@RequestBody NewsPreferencesDTO dto){
        return ResponseEntity.ok(newsService.createNewsPreferencesForUser(dto));
    }

    @GetMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> getPreferencesForCurrentUser() throws PreferencesNotFoundException {
        return ResponseEntity.ok(newsService.getNewsPreferenceByUserId());
    }

    @PutMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> updatePreferencesForCurrentUser(@RequestBody NewsPreferencesDTO dto) throws PreferencesNotFoundException {
        return ResponseEntity.ok(newsService.updateNewsPreferencesForUser(dto));
    }
}
