package com.project.trybargain.domain.comment.repository;

import com.project.trybargain.domain.comment.entity.Comment;
import com.project.trybargain.domain.comment.entity.CommentLike;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepository {

    private final EntityManager em;

    public void save(CommentLike commentLike) {
        em.persist(commentLike);
    }

    public Optional<CommentLike> findById(long user_id, long comment_id) {
        return em.createQuery("select li from CommentLike li " +
                    "join fetch li.user u " +
                    "join fetch li.comment c " +
                    "where u.id = :user_id " +
                    "and c.id = :comment_id", CommentLike.class)
                    .setParameter("user_id", user_id)
                    .setParameter("comment_id", comment_id)
                    .getResultList()
                    .stream()
                    .findAny();
    }

    public Optional<CommentLike> findByComment(Comment comment, long userId) {
        return em.createQuery("SELECT cl FROM CommentLike cl " +
                        "WHERE cl.comment = :comment AND cl.user.id = :userId", CommentLike.class)
                .setParameter("userId", userId)
                .setParameter("comment", comment)
                .getResultList()
                .stream().findAny();
    }
}
