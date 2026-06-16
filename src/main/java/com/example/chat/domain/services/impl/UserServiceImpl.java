package com.example.chat.domain.services.impl;

import com.example.chat.domain.dto.UserDto;
import com.example.chat.domain.entity.UserEntity;
import com.example.chat.domain.mapper.UserMapper;
import com.example.chat.repository.UserRepository;
import com.example.chat.domain.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getOrCreateUser(UserDto user) {
        return userRepository.findById(user.getId())
                .map(mapper::toDto)
                .orElseGet(() -> {
                    UserEntity saved = userRepository.save(mapper.toEntity(user));
                    return mapper.toDto(saved);
                });
    }
}
