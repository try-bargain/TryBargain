package com.project.trybargain.domain.comment.service;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.repository.BoardRepository;
import com.project.trybargain.domain.comment.dto.CommentRequestDto;
import com.project.trybargain.domain.comment.dto.CommentResponseDto;
import com.project.trybargain.domain.comment.entity.Comment;
import com.project.trybargain.domain.comment.entity.CommentLike;
import com.project.trybargain.domain.comment.repository.CommentLikeRepository;
import com.project.trybargain.domain.comment.repository.CommentRepository;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.repository.UserRepository;
import com.project.trybargain.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final CommentLikeRepository commentLikeRepository;


    public CommentResponseDto createComment(CommentRequestDto requestDto, long boardNo, User user) {
        Comment comment = new Comment(requestDto);
        User selectUser = userRepository.findById(user.getId()).orElseThrow(() -> new NullPointerException("해당 유저는 없습니다."));
        comment.addUser(selectUser);

        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new NullPointerException("해당 게시글은 없습니다."));
        comment.addBoard(board);

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }


    public CommentResponseDto updateComment(CommentRequestDto requestDto, long commentId, User user) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글은 없습니다."));

        if(user.getId() != findComment.getUser().getId()) {
            throw new BadCredentialsException("댓글작성자만 수정할 수 있습니다.");
        }

        findComment.updateComment(requestDto);
        return new CommentResponseDto(findComment);
    }

    public CommentResponseDto deleteComment(long commentId, User user) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글은 없습니다."));

        if(user.getId() != findComment.getUser().getId()) {
            throw new BadCredentialsException("댓글작성자만 삭제할 수 있습니다.");
        }

        findComment.deleteComment();
        return new CommentResponseDto(findComment);
    }

    public ResponseEntity<MessageResponseDto> updateCommentLike(long commentNo, User user) {

        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new NullPointerException("해당 유저는 없습니다."));
        Comment comment = commentRepository.findById(commentNo).orElseThrow(() -> new NullPointerException("해당 댓글은 없습니다."));

        CommentLike findLike = commentLikeRepository.findById(user.getId(), commentNo).orElse(null);

        if(findLike == null) {
            CommentLike commentLike = new CommentLike(comment, findUser);
            commentLikeRepository.save(commentLike);
        }else {
            comment.updateLikeCnt(findLike.isLike_yn());
            findLike.updateLikeStatus();
        }

        MessageResponseDto responseEntity = new MessageResponseDto("게시글 상태를 변경하였습니다.",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }
}
