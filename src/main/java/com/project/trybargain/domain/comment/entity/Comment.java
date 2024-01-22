package com.project.trybargain.domain.comment.entity;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.comment.dto.CommentRequestDto;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.global.entity.TimeStamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String content;

    @NotNull
    private int comment_like;

    @NotNull
    private boolean active_yn = true;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToOne(mappedBy = "comment", fetch = FetchType.LAZY)
    private CommentLike commentLike;

    public void addUser(User user) {
        this.user = user;
        user.addComment(this);
    }

    public void addBoard(Board board) {
        this.board = board;
        board.addComment(this);
    }

    public Comment(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }



}
