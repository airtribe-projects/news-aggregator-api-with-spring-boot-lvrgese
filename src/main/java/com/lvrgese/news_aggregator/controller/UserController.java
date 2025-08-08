package com.lvrgese.news_aggregator.controller;

import com.lvrgese.news_aggregator.dto.UserDTO;
import com.lvrgese.news_aggregator.exception.UserNotFoundException;
import com.lvrgese.news_aggregator.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }
}
