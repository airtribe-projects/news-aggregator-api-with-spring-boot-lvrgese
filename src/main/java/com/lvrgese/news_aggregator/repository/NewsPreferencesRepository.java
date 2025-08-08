package com.lvrgese.news_aggregator.repository;

import com.lvrgese.news_aggregator.entity.NewsPreferences;
import com.lvrgese.news_aggregator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsPreferencesRepository extends JpaRepository<NewsPreferences,Long> {

    Optional<NewsPreferences> findByUser(User user);

}
