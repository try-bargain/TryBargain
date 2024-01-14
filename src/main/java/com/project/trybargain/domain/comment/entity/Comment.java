package com.project.trybargain.domain.comment.entity;

import com.project.trybargain.global.entity.TimeStamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

//@Entity
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

    @OneToOne(mappedBy = "comment", fetch = FetchType.LAZY)
    private CommentLike commentLike;
}
