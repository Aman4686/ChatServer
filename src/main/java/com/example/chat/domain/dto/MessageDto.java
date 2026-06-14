package com.example.chat.domain.dto;

import com.example.chat.domain.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private MessageType type;
    private String content;
    private String sender;
    private Instant createdAt;
}
