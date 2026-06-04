package com.example.chat.domain.mapper;

import com.example.chat.domain.dto.UserDto;
import com.example.chat.domain.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);
}
