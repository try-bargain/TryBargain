package com.project.trybargain.domain.comment.service;

import com.project.trybargain.domain.comment.dto.CommentRequestDto;
import com.project.trybargain.domain.comment.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    public CommentResponseDto createComment(CommentRequestDto requestDto) {



        return new CommentResponseDto();
    }
}
