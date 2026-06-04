package com.example.chat.controllers;

import com.example.chat.domain.dto.UserDto;
import com.example.chat.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserDto createUser(@RequestBody UserDto user) {
        return userService.createUser(user);
    }

}
