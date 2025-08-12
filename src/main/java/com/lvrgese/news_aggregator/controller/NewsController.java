package com.lvrgese.news_aggregator.controller;

import com.lvrgese.news_aggregator.dto.GNewsResponse;
import com.lvrgese.news_aggregator.exception.GNewsFetchException;
import com.lvrgese.news_aggregator.exception.PreferencesNotFoundException;
import com.lvrgese.news_aggregator.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<GNewsResponse> getNewsForCurrentUser() throws PreferencesNotFoundException, GNewsFetchException {
        GNewsResponse response = newsService.fetchNews();
        return ResponseEntity.ok(response);
    }
}
