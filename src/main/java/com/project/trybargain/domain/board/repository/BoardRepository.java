package com.project.trybargain.domain.board.repository;

import com.project.trybargain.domain.board.entity.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public List<Board> findAll(int page, int size) {
        List<Board> boardList = em.createQuery("SELECT b FROM Board b join fetch b.category c WHERE b.active_yn = true", Board.class)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
        return boardList;
    }

    public long countBoard() {
        Long count = em.createQuery("SELECT COUNT(b) FROM Board b", Long.class)
                .getSingleResult();
        return count;
    }

    public List<Board> findAllByTitle(String query) {
        List<Board> boardList = em.createQuery("SELECT b FROM Board b WHERE b.title LIKE :query AND b.active_yn = true", Board.class)
                .setParameter("query", "%"+query+"%")
                .getResultList();
        return boardList;
    }

    public Optional<Board> findById(long id) {
        List<Board> boardList = em.createQuery("SELECT b FROM Board b WHERE b.id = :id AND b.active_yn = true", Board.class)
                .setParameter("id", id)
                .getResultList();
        return boardList.stream().findAny();
    }
}
