package com.example.chat.domain.mapper;

import com.example.chat.domain.dto.MessageDto;
import com.example.chat.domain.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "user.id", target = "userId")
    MessageDto toDto(MessageEntity entity);

    @Mapping(source = "userId", target = "user.id")
    MessageEntity toEntity(MessageDto dto);
}
