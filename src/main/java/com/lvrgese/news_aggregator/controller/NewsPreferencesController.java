package com.lvrgese.news_aggregator.controller;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.exception.ResourceAlreadyExistsException;
import com.lvrgese.news_aggregator.exception.ResourceNotFoundException;
import com.lvrgese.news_aggregator.service.NewsPreferencesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class NewsPreferencesController {

    private final NewsPreferencesService newsPreferencesService;

    public NewsPreferencesController(NewsPreferencesService newsPreferencesService) {
        this.newsPreferencesService = newsPreferencesService;
    }

    @PostMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> createPreferencesForCurrentUser(@RequestBody @Valid NewsPreferencesDTO dto)
            throws ResourceAlreadyExistsException {
        log.info("Creating news preferences for current user");
        return ResponseEntity.ok(newsPreferencesService.createNewsPreferencesForUser(dto));
    }

    @GetMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> getPreferencesForCurrentUser() throws ResourceNotFoundException {
        log.info("Retrieving news preferences for current user");
        return ResponseEntity.ok(newsPreferencesService.getNewsPreferencesForCurrentUser());
    }

    @PutMapping("/preferences")
    public ResponseEntity<NewsPreferencesDTO> updatePreferencesForCurrentUser(@RequestBody @Valid NewsPreferencesDTO dto)
            throws ResourceNotFoundException {
        log.info("Updating news preferences for current user");
        return ResponseEntity.ok(newsPreferencesService.updateNewsPreferencesForUser(dto));
    }
}
