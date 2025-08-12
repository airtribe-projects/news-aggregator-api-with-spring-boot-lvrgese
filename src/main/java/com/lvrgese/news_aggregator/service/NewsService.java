package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.GNewsResponse;
import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.exception.GNewsFetchException;
import com.lvrgese.news_aggregator.exception.PreferencesNotFoundException;
import com.lvrgese.news_aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class NewsService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    @Value("${gnews.api.key}")
    private String apiKey;


    public NewsService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    public GNewsResponse fetchNews() throws PreferencesNotFoundException, GNewsFetchException {

        User user = getCurrentUser();
        NewsPreferences pref = user.getNewsPreferences();
        if(pref == null){
            throw new PreferencesNotFoundException("No preferences set for current user");
        }

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
        try{
            return restTemplate.getForObject(uri, GNewsResponse.class);
        }
        catch (Exception ex){
            throw new GNewsFetchException("GNews API call unsuccessful");
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
