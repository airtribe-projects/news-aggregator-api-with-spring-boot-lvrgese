package com.lvrgese.news_aggregator.auth.controller;

import com.lvrgese.news_aggregator.auth.entity.RegisterRequest;
import com.lvrgese.news_aggregator.auth.entity.UserDTO;
import com.lvrgese.news_aggregator.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerNewUser(@RequestBody @Valid RegisterRequest req){
        return new ResponseEntity<UserDTO>(userService.registerUser(req), HttpStatus.CREATED) ;
    }

    @GetMapping("/register")
    public String testHello(){
        return "Hello world";
    }
}
