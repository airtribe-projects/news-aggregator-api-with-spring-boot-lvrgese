package com.lvrgese.news_aggregator.service;

import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO getUserProfile() {
        User user = getCurrentUser();
        log.info("Retrieved the profile of current user");
        return new UserDTO(user);
    }
}
