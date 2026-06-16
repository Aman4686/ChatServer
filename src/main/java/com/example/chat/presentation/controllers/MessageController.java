package com.example.chat.presentation.controllers;

import com.example.chat.domain.dto.MessageDto;
import com.example.chat.domain.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getMessages() {
        List<MessageDto> body = messageService.getMessages();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }
}
