package com.example.chat.domain.mapper;

import com.example.chat.domain.dto.MessageDto;
import com.example.chat.domain.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "text", target = "content")
    @Mapping(source = "user.name", target = "sender")
    MessageDto toDto(MessageEntity entity);

    @Mapping(source = "content", target = "text")
    MessageEntity toEntity(MessageDto dto);
}
