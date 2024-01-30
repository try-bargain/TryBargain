package com.project.trybargain.domain.board.repository;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.entity.BoardLike;
import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardLikeRepository {
    private final EntityManager em;

    public void save(BoardLike boardLike) {
        em.persist(boardLike);
    }

    public Optional<BoardLike> findByUserAndBoard(User user, Board board) {
        List<BoardLike> boardLikeList = em.createQuery("SELECT bl FROM BoardLike bl WHERE bl.user = :user AND bl.board = :board", BoardLike.class)
                .setParameter("user", user)
                .setParameter("board", board)
                .getResultList();
        return boardLikeList.stream().findFirst();
    }
}
