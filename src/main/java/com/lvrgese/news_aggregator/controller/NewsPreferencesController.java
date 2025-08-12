package com.lvrgese.news_aggregator.controller;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.exception.PreferencesNotFoundException;
import com.lvrgese.news_aggregator.service.NewsPreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsPreferencesController {

    private final NewsPreferencesService newsPreferencesService;

    public NewsPreferencesController(NewsPreferencesService newsPreferencesService) {
        this.newsPreferencesService = newsPreferencesService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserById()  {
        return ResponseEntity.ok(newsPreferencesService.getUserProfile());
    }

    @PostMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> createPreferencesForCurrentUser(@RequestBody NewsPreferencesDTO dto){
        return ResponseEntity.ok(newsPreferencesService.createNewsPreferencesForUser(dto));
    }

    @GetMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> getPreferencesForCurrentUser() throws PreferencesNotFoundException {
        return ResponseEntity.ok(newsPreferencesService.getNewsPreferencesForCurrentUser());
    }

    @PutMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> updatePreferencesForCurrentUser(@RequestBody NewsPreferencesDTO dto) throws PreferencesNotFoundException {
        return ResponseEntity.ok(newsPreferencesService.updateNewsPreferencesForUser(dto));
    }
}
