package com.project.trybargain.domain.comment.entity;

import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

//@Entity
@NoArgsConstructor
@Table(name = "comment_like")
public class CommentLike {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ColumnDefault("false")
    private boolean like_yn;
}
