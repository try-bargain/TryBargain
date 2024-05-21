package com.project.trybargain.domain.comment.entity;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment_like")
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private boolean like_yn = true;

    public CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
        comment.updateLikeCnt(false);
    }

    public void updateLikeStatus() {
        this.like_yn = !this.like_yn;
    }

    public void addComment(Comment comment) {
        this.comment = comment;
    }

    public void changeLike() {
        this.like_yn = !this.like_yn;
    }

}
