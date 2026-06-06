package com.example.chat.domain.services;

import com.example.chat.domain.dto.MessageDto;

public interface MessageService {
    MessageDto save(MessageDto messageDto);
}
