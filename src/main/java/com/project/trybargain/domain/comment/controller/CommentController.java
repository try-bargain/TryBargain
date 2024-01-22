package com.project.trybargain.domain.comment.controller;

import com.project.trybargain.domain.comment.dto.CommentRequestDto;
import com.project.trybargain.domain.comment.dto.CommentResponseDto;
import com.project.trybargain.domain.comment.service.CommentService;
import com.project.trybargain.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{boardNo}")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @PathVariable long boardNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto, boardNo, userDetails.getUser());
    }
}
