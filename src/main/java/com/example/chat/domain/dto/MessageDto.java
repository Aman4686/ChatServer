package com.example.chat.domain.dto;

import com.example.chat.domain.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {
    private MessageType type;
    private String content;
    private String sender;
    private Instant createdAt;
}
