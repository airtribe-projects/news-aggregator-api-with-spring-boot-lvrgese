package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Service
public class NewsService {

    private final WebClient gNewsClient;
    private final UserRepository userRepository;

    @Value("${gnews.api.key}")
    private String apiKey;


    public NewsService(@Qualifier("gNewsClient") WebClient gNewsClient, UserRepository userRepository) {
        this.gNewsClient = gNewsClient;
        this.userRepository = userRepository;
    }

    public Mono<String> fetchNews() {

        User user = getCurrentUser();
        NewsPreferences pref = user.getNewsPreferences();

        URI uri = UriComponentsBuilder
                .fromUriString("https://gnews.io/api/v4")
                .path("/search")
                .queryParam("q", pref.getQuery())
                .queryParamIfPresent("lang", Optional.ofNullable(pref.getLang()))
                .queryParamIfPresent("country", Optional.ofNullable(pref.getCountry()))
                .queryParam("max", pref.getCount())
                .queryParamIfPresent("sortBy", Optional.ofNullable(pref.getSortBy()))
                .queryParam("apikey", apiKey)
                .build(true) // keep encoded params as-is
                .toUri();

        return gNewsClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(body -> System.out.println("📩 Response: " + body));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
