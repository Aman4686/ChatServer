package com.example.chat.domain.mapper;

import com.example.chat.domain.dto.UserDto;
import com.example.chat.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "password", target = "password")
    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);
}
