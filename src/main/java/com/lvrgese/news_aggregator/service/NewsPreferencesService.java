package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.NewsPreferencesDTO;
import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.entity.Country;
import com.lvrgese.news_aggregator.entity.Language;
import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.exception.PreferencesNotFoundException;
import com.lvrgese.news_aggregator.repository.NewsPreferencesRepository;
import com.lvrgese.news_aggregator.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NewsPreferencesService {

    private final NewsPreferencesRepository newsPreferencesRepository;

    private final UserRepository userRepository;

    public NewsPreferencesService(NewsPreferencesRepository newsPreferencesRepository, UserRepository userRepository) {
        this.newsPreferencesRepository = newsPreferencesRepository;
        this.userRepository = userRepository;
    }

    public UserDTO getUserProfile() {
        User user = getCurrentUser();
        NewsPreferencesDTO pref = null;
        try {
            pref = getNewsPreferencesForCurrentUser();
        }
        catch (Exception ignored){}

        return new UserDTO(user,pref);
    }

    public NewsPreferencesDTO getNewsPreferencesForCurrentUser() throws PreferencesNotFoundException {
        User user = getCurrentUser();
        if(user.getNewsPreferences() == null){
            throw new PreferencesNotFoundException("No preferences saved for user with id "+user.getUserId());
        }
        return mapToDto(user.getNewsPreferences());
    }

    public NewsPreferencesDTO createNewsPreferencesForUser(NewsPreferencesDTO pref){

        User user = getCurrentUser();
        NewsPreferences newPref =new NewsPreferences.builder()
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

    public NewsPreferencesDTO updateNewsPreferencesForUser(NewsPreferencesDTO pref) throws PreferencesNotFoundException {
        User user = getCurrentUser();
        if(user.getNewsPreferences() == null){
            throw new PreferencesNotFoundException("No preferences saved for user with id "+user.getUserId());
        }
        NewsPreferences currentPref = user.getNewsPreferences();
        NewsPreferences newPref =new NewsPreferences.builder()
                .prefId(currentPref.getPrefId())
                .query(pref.getQuery())
                .country(Country.isValid(pref.getCountry())? pref.getCountry() :currentPref.getCountry())
                .lang(Language.isValid(pref.getLang()) ? pref.getLang() : currentPref.getLang())
                .count(pref.getCount())
                .sortBy(getValidatedSortBy(pref.getSortBy()))
                .user(user)
                .build();


        NewsPreferences savedPref =  newsPreferencesRepository.save(newPref);
        return mapToDto(savedPref);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
