package com.project.trybargain.domain.chat.repository;

import com.project.trybargain.domain.chat.entity.ChattingRoom;
import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final EntityManager em;

    public void save(ChattingRoom chattingRome) {
        em.persist(chattingRome);
    }

    public Optional<ChattingRoom> findById(long roomId) {
       return em.createQuery("SELECT cr FROM ChattingRoom cr WHERE cr.id = :roomId", ChattingRoom.class)
               .setParameter("roomId", roomId)
               .getResultList()
               .stream()
               .findAny();
    }

    public List<ChattingRoom> findByAllUser(User user) {
        return em.createQuery("SELECT cr FROM ChattingRoom cr WHERE cr.user = :user", ChattingRoom.class)
                .setParameter("user", user)
                .getResultList();
    }
}
