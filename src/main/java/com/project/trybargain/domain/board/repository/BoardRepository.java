package com.project.trybargain.domain.board.repository;

import com.project.trybargain.domain.board.entity.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public List<Board> findAllByTop100() {
        List<Board> findBoards = em.createQuery("SELECT b FROM Board b LIMIT 100", Board.class)
                .getResultList();
        return findBoards;
    }
}
