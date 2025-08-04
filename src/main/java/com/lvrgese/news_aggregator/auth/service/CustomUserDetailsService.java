package com.lvrgese.news_aggregator.auth.service;

import com.lvrgese.news_aggregator.auth.entity.CustomUserDetails;
import com.lvrgese.news_aggregator.auth.entity.User;
import com.lvrgese.news_aggregator.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with name: " + username);
        }
        return new CustomUserDetails(user);
    }
}
