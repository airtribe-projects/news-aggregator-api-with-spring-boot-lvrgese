package com.lvrgese.news_aggregator.controller;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.exception.PreferencesNotFoundException;
import com.lvrgese.news_aggregator.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/preferences/{id}")
    public ResponseEntity<NewsPreferencesDTO> createPreferences(@RequestBody NewsPreferencesDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(newsService.createNewsPreferencesForUser(dto,id));
    }

    @GetMapping("/preferences/{id}")
    public ResponseEntity<NewsPreferencesDTO> getPreferencesForUser(@PathVariable Long id) throws PreferencesNotFoundException {
        return ResponseEntity.ok(newsService.getNewsPreferenceByUserId(id));
    }

    @PutMapping("/preferences/{id}")
    public ResponseEntity<NewsPreferencesDTO> updatePreferencesForUser(@RequestBody NewsPreferencesDTO dto, @PathVariable Long id) throws PreferencesNotFoundException {
        return ResponseEntity.ok(newsService.updateNewsPreferencesForUser(dto,id));
    }
}
