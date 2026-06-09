package com.example.chat.domain.services.impl;

import com.example.chat.domain.dto.MessageDto;
import com.example.chat.domain.entity.MessageEntity;
import com.example.chat.domain.entity.UserEntity;
import com.example.chat.domain.mapper.MessageMapper;
import com.example.chat.domain.services.MessageService;
import com.example.chat.repository.MessageRepository;
import com.example.chat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public MessageDto save(MessageDto dto) {
        UserEntity user = userRepository.findByName(dto.getSender())
                .orElseGet(() -> userRepository.save(UserEntity.builder().name(dto.getSender()).build()));

        MessageEntity entity = MessageEntity.builder()
                .text(dto.getContent())
                .type(dto.getType())
                .user(user)
                .build();

        return messageMapper.toDto(messageRepository.save(entity));
    }

    @Override
    public List<MessageDto> getMessages() {
        return messageMapper.toDtoList(messageRepository.findAll());
    }
}
