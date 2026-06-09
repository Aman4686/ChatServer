package com.example.chat.domain.services;

import com.example.chat.domain.dto.MessageDto;

import java.util.List;

public interface MessageService {
    MessageDto save(MessageDto messageDto);
    List<MessageDto> getMessages();
}
