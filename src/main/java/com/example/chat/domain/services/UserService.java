package com.example.chat.domain.services;

import com.example.chat.domain.dto.UserDto;

public interface UserService {

    UserDto getOrCreateUser(UserDto user);
}
