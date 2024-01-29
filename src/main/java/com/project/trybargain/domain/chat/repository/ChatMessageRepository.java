package com.project.trybargain.domain.chat.repository;

import com.project.trybargain.domain.chat.entity.ChattingMessage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {

    private final EntityManager em;

    public void save(ChattingMessage chattingMessage) {
        em.persist(chattingMessage);
    }

}
