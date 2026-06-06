package com.example.chat.controllers;

import com.example.chat.domain.dto.MessageDto;
import com.example.chat.domain.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(ChatWebSocketHandler.class);
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageService messageService;

    public ChatWebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("afterConnectionEstablished");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage {}", message);
        MessageDto messageDto = objectMapper.readValue(message.getPayload(), MessageDto.class);

        if (messageDto.getType() == MessageDto.Type.JOIN) {
            session.getAttributes().put("username", messageDto.getSender());
        }

        if (messageDto.getType() == MessageDto.Type.CHAT) {
            messageDto = messageService.save(messageDto);
        }

        broadcast(objectMapper.writeValueAsString(messageDto));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("afterConnectionClosed");
        sessions.remove(session);

        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            MessageDto leave = MessageDto.builder()
                    .type(MessageDto.Type.LEAVE)
                    .sender(username)
                    .content(username + " left the chat")
                    .build();
            broadcast(objectMapper.writeValueAsString(leave));
        }
    }

    private void broadcast(String payload) throws Exception {
        TextMessage message = new TextMessage(payload);
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(message);
            }
        }
    }
}
