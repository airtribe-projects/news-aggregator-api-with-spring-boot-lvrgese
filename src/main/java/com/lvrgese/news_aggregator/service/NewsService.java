package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.exception.PreferencesNotFoundException;
import com.lvrgese.news_aggregator.repository.NewsPreferencesRepository;
import com.lvrgese.news_aggregator.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsPreferencesRepository newsPreferencesRepository;

    private final UserRepository userRepository;

    public NewsService(NewsPreferencesRepository newsPreferencesRepository, UserRepository userRepository) {
        this.newsPreferencesRepository = newsPreferencesRepository;
        this.userRepository = userRepository;
    }

    public NewsPreferencesDTO getNewsPreferenceByUserId(Long userId) throws PreferencesNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found with Id "+userId));
        if(user.getNewsPreferences() == null){
            throw new PreferencesNotFoundException("No preferences saved for user with id "+user.getUserId());
        }
        return mapToDto(user.getNewsPreferences());
    }

    public NewsPreferencesDTO createNewsPreferencesForUser(NewsPreferencesDTO pref, Long userId){

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found with Id "+userId));

        NewsPreferences savedPref =  newsPreferencesRepository.save(buildPreferences(pref,user));
        return mapToDto(savedPref);
    }

    public NewsPreferencesDTO updateNewsPreferencesForUser(NewsPreferencesDTO pref, Long id) throws PreferencesNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found with Id "+id));
        if(user.getNewsPreferences() == null){
            throw new PreferencesNotFoundException("No preferences saved for user with id "+user.getUserId());
        }
        NewsPreferences savedPref =  newsPreferencesRepository.save(buildPreferences(pref,user));
        return mapToDto(savedPref);
    }

    private NewsPreferencesDTO mapToDto(NewsPreferences pref){

        return new NewsPreferencesDTO(pref.getPrefId(),pref.getQuery(),pref.getLang(),
                pref.getCountry(),pref.getCount(),pref.getSortBy());
    }

    private String getValidatedSortBy(String sortBy){
        if(!sortBy.equals("publishedAt") && ! sortBy.equals("relevance") ){
            return "publishedAt";
        }
        return sortBy;
    }

    private NewsPreferences buildPreferences(NewsPreferencesDTO pref,User user){
        return new NewsPreferences.builder()
                .query(pref.getQuery())
                .country(pref.getCountry())
                .lang(pref.getLang())
                .count(pref.getCount())
                .sortBy(getValidatedSortBy(pref.getSortBy()))
                .user(user)
                .build();
    }
}
