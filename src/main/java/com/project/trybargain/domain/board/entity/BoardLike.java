package com.project.trybargain.domain.board.entity;

import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private boolean like_yn = true;

    public BoardLike(Board board, User user) {
        this.board = board;
        this.user = user;
    }

    public void changeLike() {
        this.like_yn = !this.like_yn;
    }

    public void addBoard(Board board) {
        this.board = board;
    }
}
