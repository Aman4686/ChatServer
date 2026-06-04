package com.example.chat.domain.services.impl;

import com.example.chat.domain.dto.UserDto;
import com.example.chat.domain.entity.UserEntity;
import com.example.chat.domain.mapper.UserMapper;
import com.example.chat.repository.UserRepository;
import com.example.chat.domain.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = userRepository.save(entity);
        return mapper.toDto(saved);
    }
}
