package com.project.trybargain.domain.board.repository;

import com.project.trybargain.domain.board.dto.BoardResponseDto;
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
        List<Board> boardList = em.createQuery("SELECT b FROM Board b", Board.class)
                .setMaxResults(100)
                .getResultList();
        return boardList;
    }

    public List<Board> findAllByTitle(String query) {
        List<Board> boardList = em.createQuery("SELECT b FROM Board b WHERE b.title LIKE :query", Board.class)
                .setParameter("query", "%"+query+"%")
                .getResultList();

        return boardList;
    }
}
