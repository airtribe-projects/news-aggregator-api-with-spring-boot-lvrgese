package com.lvrgese.news_aggregator.controller;

import com.lvrgese.news_aggregator.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ResponseEntity<Mono<String>> getNewsForCurrentUser(){
        return ResponseEntity.ok(newsService.fetchNews());
    }
}
