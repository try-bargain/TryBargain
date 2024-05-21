package com.project.trybargain.domain.comment.entity;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.entity.BoardLike;
import com.project.trybargain.domain.board.entity.BoardStatusEnum;
import com.project.trybargain.domain.comment.dto.CommentRequestDto;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.global.entity.TimeStamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<CommentLike> commentLike = new ArrayList<>();

    public void addUser(User user) {
        this.user = user;
        user.addComment(this);
    }

    public void addBoard(Board board) {
        this.board = board;
    }

    public Comment(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void updateComment(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void deleteComment() {
        this.active_yn = false;
    }

    public void updateLikeCnt(boolean status) {
        if (status) comment_like--;
        else comment_like++;
    }

    public void addLikeList(CommentLike commentLike) {
        this.commentLike.add(commentLike);
        commentLike.addComment(this);
    }
}
