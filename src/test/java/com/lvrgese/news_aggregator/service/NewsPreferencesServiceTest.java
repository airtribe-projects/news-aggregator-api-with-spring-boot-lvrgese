package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.exception.ResourceAlreadyExistsException;
import com.lvrgese.news_aggregator.exception.ResourceNotFoundException;
import com.lvrgese.news_aggregator.repository.NewsPreferencesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsPreferencesServiceTest {

    @Mock
    private NewsPreferencesRepository newsPreferencesRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private NewsPreferencesService newsPreferencesService;

    @Test
    void getNewsPreferences_success() throws ResourceNotFoundException {
        User user = new User();
        NewsPreferences pref = NewsPreferences.builder()
                .prefId(1L)
                .query("tech")
                .lang("en")
                .country("us")
                .count(10)
                .sortBy("publishedAt")
                .user(user)
                .build();
        user.setNewsPreferences(pref);

        when(userService.getCurrentUser()).thenReturn(user);

        NewsPreferencesDTO dto = newsPreferencesService.getNewsPreferencesForCurrentUser();

        assertEquals("tech", dto.getQuery());
        assertEquals("en", dto.getLang());
    }

    @Test
    void getNewsPreferences_notFound() {
        User user = new User();
        user.setNewsPreferences(null);

        when(userService.getCurrentUser()).thenReturn(user);

        assertThrows(ResourceNotFoundException.class,
                () -> newsPreferencesService.getNewsPreferencesForCurrentUser());
    }

    @Test
    void createNewsPreferences_success() throws ResourceAlreadyExistsException {
        User user = new User();
        user.setNewsPreferences(null);

        NewsPreferencesDTO inputDto = new NewsPreferencesDTO(null, "sports", "en", "in", 5, "publishedAt");

        NewsPreferences savedPref = NewsPreferences.builder()
                .prefId(1L)
                .query("sports")
                .lang("en")
                .country("in")
                .count(5)
                .sortBy("publishedAt")
                .user(user)
                .build();

        when(userService.getCurrentUser()).thenReturn(user);
        when(newsPreferencesRepository.save(any(NewsPreferences.class))).thenReturn(savedPref);

        NewsPreferencesDTO result = newsPreferencesService.createNewsPreferencesForUser(inputDto);

        assertNotNull(result.getPrefId());
        assertEquals("sports", result.getQuery());
    }

    @Test
    void createNewsPreferences_alreadyExists() {
        User user = new User();
        user.setNewsPreferences(new NewsPreferences()); // already has pref

        when(userService.getCurrentUser()).thenReturn(user);

        assertThrows(ResourceAlreadyExistsException.class,
                () -> newsPreferencesService.createNewsPreferencesForUser(
                        new NewsPreferencesDTO(null, "sports", "en", "in", 5, "publishedAt")
                ));
    }

    @Test
    void updateNewsPreferences_success() throws ResourceNotFoundException {
        User user = new User();
        NewsPreferences pref = NewsPreferences.builder()
                .prefId(1L)
                .query("old")
                .lang("en")
                .country("in")
                .count(3)
                .sortBy("relevance")
                .user(user)
                .build();
        user.setNewsPreferences(pref);

        NewsPreferencesDTO updateDto = new NewsPreferencesDTO(null, "updated", "fr", "us", 7, "publishedAt");

        NewsPreferences updatedPref = NewsPreferences.builder()
                .prefId(1L)
                .query("updated")
                .lang("fr")
                .country("us")
                .count(7)
                .sortBy("publishedAt")
                .user(user)
                .build();

        when(userService.getCurrentUser()).thenReturn(user);
        when(newsPreferencesRepository.save(any(NewsPreferences.class))).thenReturn(updatedPref);

        NewsPreferencesDTO result = newsPreferencesService.updateNewsPreferencesForUser(updateDto);

        assertEquals("updated", result.getQuery());
        assertEquals("fr", result.getLang());
        assertEquals("us", result.getCountry());
    }

    @Test
    void updateNewsPreferences_notFound() {
        User user = new User();
        user.setNewsPreferences(null);

        when(userService.getCurrentUser()).thenReturn(user);

        assertThrows(ResourceNotFoundException.class,
                () -> newsPreferencesService.updateNewsPreferencesForUser(
                        new NewsPreferencesDTO(null, "update", "en", "in", 5, "publishedAt")
                ));
    }
}

