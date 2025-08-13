package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.GNewsResponse;
import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.exception.GNewsFetchException;
import com.lvrgese.news_aggregator.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserService userService;

    @InjectMocks
    private NewsService newsService;

    @BeforeEach
    void setup() {
        // Manually inject the API key since @Value won't work in plain unit test
        ReflectionTestUtils.setField(newsService, "apiKey", "test-api-key");
    }

    @Test
    void fetchNews_success() throws GNewsFetchException, ResourceNotFoundException {
        User user = new User();
        NewsPreferences prefs = NewsPreferences.builder()
                .query("technology")
                .lang("en")
                .country("us")
                .count(5)
                .sortBy("publishedAt")
                .user(user)
                .build();
        user.setNewsPreferences(prefs);

        GNewsResponse mockResponse = new GNewsResponse();
        mockResponse.setTotalArticles(10);

        when(userService.getCurrentUser()).thenReturn(user);
        when(restTemplate.getForObject(any(URI.class), eq(GNewsResponse.class)))
                .thenReturn(mockResponse);
        GNewsResponse result = newsService.fetchNews();

        assertNotNull(result);
        assertEquals(10, result.getTotalArticles());
        verify(restTemplate, times(1)).getForObject(any(URI.class), eq(GNewsResponse.class));
    }

    @Test
    void fetchNews_noPreferences() {
        User user = new User();
        user.setNewsPreferences(null);

        when(userService.getCurrentUser()).thenReturn(user);
        assertThrows(ResourceNotFoundException.class, () -> newsService.fetchNews());
    }

    @Test
    void fetchNews_gnewsApiFailure() {
        User user = new User();
        NewsPreferences prefs = NewsPreferences.builder()
                .query("sports")
                .lang("en")
                .country("in")
                .count(3)
                .sortBy("relevance")
                .user(user)
                .build();
        user.setNewsPreferences(prefs);

        when(userService.getCurrentUser()).thenReturn(user);
        when(restTemplate.getForObject(any(URI.class), eq(GNewsResponse.class)))
                .thenThrow(new RuntimeException("API failure"));
        assertThrows(GNewsFetchException.class, () -> newsService.fetchNews());
    }
}
