package com.project.trybargain.domain.user.repository;

import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void join(User user) {
        em.persist(user);
    }

    public User findUserById(String user_id) {
        User user = em.find(User.class, user_id);
        return user;
    }






}
