package com.project.trybargain.domain.comment.controller;

import com.project.trybargain.domain.comment.dto.CommentRequestDto;
import com.project.trybargain.domain.comment.dto.CommentResponseDto;
import com.project.trybargain.domain.comment.service.CommentService;
import com.project.trybargain.global.dto.MessageResponseDto;
import com.project.trybargain.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/comment/{commentNo}")
    public CommentResponseDto updateComment(@RequestBody CommentRequestDto requestDto, @PathVariable long commentNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(requestDto, commentNo, userDetails.getUser());
    }

    @DeleteMapping("/comment/{commentNo}")
    public CommentResponseDto deleteComment(@PathVariable long commentNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentNo, userDetails.getUser());
    }

    @PostMapping("/comment/like/{commentNo}")
    public ResponseEntity<MessageResponseDto> changeCommentLike(@PathVariable long commentNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateCommentLike(commentNo, userDetails.getUser());
    }
}
