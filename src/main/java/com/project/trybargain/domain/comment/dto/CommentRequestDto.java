package com.project.trybargain.domain.comment.dto;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;
}
