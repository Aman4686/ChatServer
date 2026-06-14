package com.example.chat.repository;

import com.example.chat.domain.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    List<MessageEntity> findAllByOrderByCreatedAtAsc();
}

