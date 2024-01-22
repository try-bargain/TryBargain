package com.project.trybargain.domain.comment.service;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.repository.BoardRepository;
import com.project.trybargain.domain.comment.dto.CommentRequestDto;
import com.project.trybargain.domain.comment.dto.CommentResponseDto;
import com.project.trybargain.domain.comment.entity.Comment;
import com.project.trybargain.domain.comment.repository.CommentRepository;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public CommentResponseDto createComment(CommentRequestDto requestDto, long boardNo, User user) {
        Comment comment = new Comment(requestDto);
        User selectUser = userRepository.findById(user.getId()).orElseThrow(() -> new NullPointerException("해당 유저는 없습니다."));
        comment.addUser(selectUser);

        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException("해당 게시글은 없습니다."));
        comment.addBoard(board);

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }
}
