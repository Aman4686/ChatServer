package com.example.chat.controllers;

import com.example.chat.domain.dto.UserDto;
import com.example.chat.domain.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDto> getOrCreateUser(@RequestBody UserDto user) {
        log.info("getOrCreateUser ", user);
        UserDto body = userService.getOrCreateUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }


}
