package com.lvrgese.news_aggregator.controller;

import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserById()  {
        return ResponseEntity.ok(userService.getUserProfile());
    }
}
