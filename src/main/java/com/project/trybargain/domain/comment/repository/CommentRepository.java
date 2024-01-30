package com.project.trybargain.domain.comment.repository;

import com.project.trybargain.domain.comment.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Optional<Comment> findById(long id) {
        return em.createQuery("select c from Comment  c where c.active_yn = true and c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findAny();
    }


}
