package com.lvrgese.news_aggregator.auth.service;

import com.lvrgese.news_aggregator.auth.entity.RegisterRequest;
import com.lvrgese.news_aggregator.auth.entity.User;
import com.lvrgese.news_aggregator.auth.entity.UserDTO;
import com.lvrgese.news_aggregator.auth.entity.UserRole;
import com.lvrgese.news_aggregator.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(RegisterRequest req){
        User user = new User.builder()
                .username(req.getUsername())
                .name(req.getName())
                .password(passwordEncoder.encode(req.getPassword()))
                .isEnabled(true)
                .userRole(UserRole.ROLE_ADMIN)
                .build();
        User savedUser =  userRepository.save(user);

        return new UserDTO(savedUser.getUserId(), savedUser.getName(), savedUser.getUsername());
    }
}
