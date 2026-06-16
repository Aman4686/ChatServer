package com.example.chat.presentation.webSocket;

import com.example.chat.domain.MessageType;
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

    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    public ChatWebSocketHandler(ObjectMapper objectMapper, MessageService messageService) {
        this.objectMapper = objectMapper;
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("afterConnectionEstablished");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage {}", message.getPayload());
        MessageDto messageDto = objectMapper.readValue(message.getPayload(), MessageDto.class);

        String userName = messageDto.getUser().getName();

        if (messageDto.getType() == MessageType.JOIN && userName != null) {
            session.getAttributes().put("userName", userName);
        }

        if (messageDto.getType() == MessageType.CHAT) {
            messageDto = messageService.save(messageDto);
        }

        broadcast(objectMapper.writeValueAsString(messageDto));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("afterConnectionClosed");
        sessions.remove(session);

        String username = (String) session.getAttributes().get("userName");

        if (username != null) {
            MessageDto leave = MessageDto.builder()
                    .type(MessageType.LEAVE)
                    .content(username + " left the chat")
                    .build();
            broadcast(objectMapper.writeValueAsString(leave));
        }
    }

    private void broadcast(String payload) throws Exception {
        TextMessage message = new TextMessage(payload);
        for (WebSocketSession s : sessions) {
            synchronized (s) {
                if (s.isOpen()) {
                    s.sendMessage(message);
                }
            }
        }
    }
}
