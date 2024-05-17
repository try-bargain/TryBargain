package com.project.trybargain.domain.comment.dto;

import com.project.trybargain.domain.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private long commentId;
    private String content;
    private String userId;
    private int likeCnt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getUser_id();
        this.likeCnt = comment.getComment_like();
    }

    public CommentResponseDto(Comment comment, String userId) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.userId = userId;
        this.likeCnt = comment.getComment_like();
    }
}
