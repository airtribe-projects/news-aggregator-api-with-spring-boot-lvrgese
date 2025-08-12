package com.lvrgese.news_aggregator.auth.service;

import com.lvrgese.news_aggregator.auth.entity.*;
import com.lvrgese.news_aggregator.exception.ResourceAlreadyExistsException;
import com.lvrgese.news_aggregator.repository.UserRepository;
import com.lvrgese.news_aggregator.auth.util.JwtUtil;
import com.lvrgese.news_aggregator.entity.User;
import com.lvrgese.news_aggregator.entity.UserRole;
import com.lvrgese.news_aggregator.exception.InvalidCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse registerUser(RegisterRequest req) throws ResourceAlreadyExistsException {

        if(userRepository.findByUsername(req.getUsername()).isPresent()){
            log.warn("Attempt to register existing username: {}", req.getUsername());
            throw new ResourceAlreadyExistsException("User already exists. Please login");
        }
        User user = User.builder()
                .username(req.getUsername())
                .name(req.getName())
                .password(passwordEncoder.encode(req.getPassword()))
                .isEnabled(true)
                .userRole(UserRole.ROLE_USER)
                .build();
        User savedUser =  userRepository.save(user);
        log.info("Registration successful for username: {}", req.getUsername());

        return new AuthResponse(savedUser.getUserId(), savedUser.getName(), savedUser.getUsername(),
                JwtUtil.generateJwtToken(user.getUsername()));
    }

    public AuthResponse loginUser(LoginRequest loginRequest) throws InvalidCredentialsException {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(auth);
            User currentUser = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
            log.info("Login successful for username: {}", loginRequest.getUsername());
            return new AuthResponse(currentUser.getUserId(), currentUser.getName(), currentUser.getUsername(),
                    JwtUtil.generateJwtToken(currentUser.getUsername()));
        }
        catch (Exception ex){
            log.error("Authentication failed for user: {}", loginRequest.getUsername(), ex);
            throw  new InvalidCredentialsException("Login failed");
        }
    }
}
