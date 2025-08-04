package com.lvrgese.news_aggregator.auth.repository;

import com.lvrgese.news_aggregator.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
