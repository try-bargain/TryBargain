package com.project.trybargain.domain.chat.repository;

import com.project.trybargain.domain.chat.entity.ChattingMessage;
import com.project.trybargain.domain.chat.entity.ChattingRoom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {

    private final EntityManager em;

    public void save(ChattingMessage chattingMessage) {
        em.persist(chattingMessage);
    }

    public List<ChattingMessage> findByChattingRoom(ChattingRoom chattingRoom) {
        return em.createQuery("SELECT cm FROM ChattingMessage cm WHERE cm.chattingRoom = :chattingRoom", ChattingMessage.class)
                .setParameter("chattingRoom", chattingRoom)
                .getResultList();
    }
}
