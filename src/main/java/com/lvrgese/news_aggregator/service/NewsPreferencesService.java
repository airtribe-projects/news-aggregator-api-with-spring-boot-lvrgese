package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.entity.Country;
import com.lvrgese.news_aggregator.entity.Language;
import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.exception.ResourceAlreadyExistsException;
import com.lvrgese.news_aggregator.exception.ResourceNotFoundException;
import com.lvrgese.news_aggregator.repository.NewsPreferencesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsPreferencesService {

    private final NewsPreferencesRepository newsPreferencesRepository;

    private final UserService userService;

    public NewsPreferencesService(NewsPreferencesRepository newsPreferencesRepository, UserService userService) {
        this.newsPreferencesRepository = newsPreferencesRepository;
        this.userService = userService;
    }

    public NewsPreferencesDTO getNewsPreferencesForCurrentUser() throws ResourceNotFoundException {
        User user = userService.getCurrentUser();
        if(user.getNewsPreferences() == null){
            log.warn("No preferences saved for current user. Retrieval failed");
            throw new ResourceNotFoundException("No preferences saved for current user ");
        }
        log.debug("Successfully retrieved News preferences of current user: {}",user.getNewsPreferences());
        return mapToDto(user.getNewsPreferences());
    }

    public NewsPreferencesDTO createNewsPreferencesForUser(NewsPreferencesDTO pref) throws ResourceAlreadyExistsException {

        User user = userService.getCurrentUser();
        if(user.getNewsPreferences() != null){
            log.warn("Trying to create new preferences for current user, But preferences already  exist");
            throw new ResourceAlreadyExistsException("News preferences already exits. Please update");
        }
        log.debug("Creating preferences: {}", pref);
        NewsPreferences newPref =NewsPreferences.builder()
                .query(pref.getQuery())
                .country(Country.isValid(pref.getCountry())? pref.getCountry() : null)
                .lang(Language.isValid(pref.getLang()) ? pref.getLang() : null)
                .count(pref.getCount())
                .sortBy(getValidatedSortBy(pref.getSortBy()))
                .user(user)
                .build();
        NewsPreferences savedPref =  newsPreferencesRepository.save(newPref);
        return mapToDto(savedPref);
    }

    public NewsPreferencesDTO updateNewsPreferencesForUser(NewsPreferencesDTO pref) throws ResourceNotFoundException {
        User user = userService.getCurrentUser();
        if(user.getNewsPreferences() == null){
            log.warn("No preferences saved for current user. Update failed");
            throw new ResourceNotFoundException("No preferences saved for current user");
        }
        NewsPreferences currentPref = user.getNewsPreferences();
        NewsPreferences newPref = NewsPreferences.builder()
                .prefId(currentPref.getPrefId())
                .query(pref.getQuery())
                .country(Country.isValid(pref.getCountry())? pref.getCountry() :currentPref.getCountry())
                .lang(Language.isValid(pref.getLang()) ? pref.getLang() : currentPref.getLang())
                .count(pref.getCount())
                .sortBy(getValidatedSortBy(pref.getSortBy()))
                .user(user)
                .build();
        log.debug("Successfully updated news preferences of current user : {}",newPref);


        NewsPreferences savedPref =  newsPreferencesRepository.save(newPref);
        return mapToDto(savedPref);
    }

    private NewsPreferencesDTO mapToDto(NewsPreferences pref){

        return new NewsPreferencesDTO(pref.getPrefId(),pref.getQuery(),pref.getLang(),
                pref.getCountry(),pref.getCount(),pref.getSortBy());
    }

    private String getValidatedSortBy(String sortBy){

        if(sortBy == null)
            return null;
        if(!sortBy.equals("publishedAt") && ! sortBy.equals("relevance") ){
            return "publishedAt";
        }
        return sortBy;
    }
}
