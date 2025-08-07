package com.lvrgese.news_aggregator.auth.controller;

import com.lvrgese.news_aggregator.auth.entity.LoginRequest;
import com.lvrgese.news_aggregator.auth.entity.RegisterRequest;
import com.lvrgese.news_aggregator.auth.entity.AuthResponse;
import com.lvrgese.news_aggregator.auth.service.AuthService;
import com.lvrgese.news_aggregator.exception.InvalidCredentialsException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerNewUser(@RequestBody @Valid RegisterRequest req){
        return new ResponseEntity<AuthResponse>(authService.registerUser(req), HttpStatus.CREATED) ;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest) throws InvalidCredentialsException {
        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }
}
