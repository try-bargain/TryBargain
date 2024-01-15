package com.project.trybargain.domain.comment.entity;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.comment.dto.CommentRequestDto;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.global.entity.TimeStamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
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
    @ColumnDefault("true")
    private boolean active_yn;

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
}