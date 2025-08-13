package com.lvrgese.news_aggregator.auth.controller;

import com.lvrgese.news_aggregator.auth.entity.LoginRequest;
import com.lvrgese.news_aggregator.auth.entity.RegisterRequest;
import com.lvrgese.news_aggregator.auth.entity.AuthResponse;
import com.lvrgese.news_aggregator.auth.service.AuthService;
import com.lvrgese.news_aggregator.exception.InvalidCredentialsException;
import com.lvrgese.news_aggregator.exception.ResourceAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerNewUser(@RequestBody @Valid RegisterRequest req) throws ResourceAlreadyExistsException {
        log.info("Register request for username: {}", req.getUsername());
        return new ResponseEntity<AuthResponse>(authService.registerUser(req), HttpStatus.CREATED) ;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest) throws InvalidCredentialsException {
        log.info("Login request for username: {}", loginRequest.getUsername());
        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }
}
