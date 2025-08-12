package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.GNewsResponse;
import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.exception.GNewsFetchException;
import com.lvrgese.news_aggregator.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@Slf4j
public class NewsService {

    private final RestTemplate restTemplate;
    private final UserService userService;
    @Value("${gnews.api.key}")
    private String apiKey;


    public NewsService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public GNewsResponse fetchNews() throws ResourceNotFoundException, GNewsFetchException {

        User user = userService.getCurrentUser();
        log.info("Fetching news for current user");
        NewsPreferences pref = user.getNewsPreferences();
        if(pref == null){
            throw new ResourceNotFoundException("No preferences set for current user");
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
        log.debug("Constructed GNews API request: {}", uri);
        try{
            return restTemplate.getForObject(uri, GNewsResponse.class);
        }
        catch (Exception ex){
            log.error("GNews API call unsuccessful", ex);
            throw new GNewsFetchException("GNews API call unsuccessful");
        }
    }
}
