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
import com.project.trybargain.global.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final RedisRepository redisRepository;


    public CommentResponseDto createComment(CommentRequestDto requestDto, long boardNo, User user) {
        Comment comment = new Comment(requestDto);
        User selectUser = findUser(user.getId());
        comment.addUser(selectUser);

        Board board = findBoard(boardNo);
        comment.addBoard(board);

        commentRepository.save(comment);
        return new CommentResponseDto(comment, selectUser.getUser_id());
    }


    public CommentResponseDto updateComment(CommentRequestDto requestDto, long commentId, User user) {
        Comment findComment = findComment(commentId);

        if(user.getId() != findComment.getUser().getId()) {
            throw new BadCredentialsException("댓글작성자만 수정할 수 있습니다.");
        }

        findComment.updateComment(requestDto);
        return new CommentResponseDto(findComment, user.getUser_id());
    }

    public CommentResponseDto deleteComment(long commentId, User user) {
        Comment findComment = findComment(commentId);

        if(user.getId() != findComment.getUser().getId()) {
            throw new BadCredentialsException("댓글작성자만 삭제할 수 있습니다.");
        }

        findComment.deleteComment();
        return new CommentResponseDto(findComment, user.getUser_id());
    }

    public ResponseEntity<MessageResponseDto> updateCommentLike(long commentNo, User user) {

        User findUser = findUser(user.getId());
        Comment comment = findComment(commentNo);

        if (redisRepository.isEmptySetValue("comment:like:" + comment.getId(), findUser.getId())) {
            redisRepository.deleteSetValue("comment:like:" + comment.getId(), findUser.getId());
            MessageResponseDto responseEntity = new MessageResponseDto("댓글 좋아요를 취소하였습니다.",200);
            return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
        }

        redisRepository.saveSet("comment:like:" + comment.getId(), findUser.getId());
        MessageResponseDto responseEntity = new MessageResponseDto("댓글 좋아요를 성공하였습니다.",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);

//        CommentLike findLike = commentLikeRepository.findById(user.getId(), commentNo).orElse(null);
//
//        if(findLike == null) {
//            CommentLike commentLike = new CommentLike(comment, findUser);
//            commentLikeRepository.save(commentLike);
//        }else {
//            comment.updateLikeCnt(findLike.isLike_yn());
//            findLike.updateLikeStatus();
//        }

//        MessageResponseDto responseEntity = new MessageResponseDto("게시글 상태를 변경하였습니다.",200);
//        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    @Transactional
    public void commentLikeUpdate() {

        // 전체 게시글 조회
        commentRepository.findAllBydifCommentLike().forEach(comment -> {
            /*
             * 캐시가 오류가 난 경우 주의 해야한다. - 캐시는 별도로 백업을 이용해서 유지한다.
             * 캐시가 오류가 나고 백업이 되기전에 좋아요 수치가 DB에 반영된다면 ? 트레이드 오프인가 ?
             */
            String likeKey = "comment:like:" + comment.getId();
            // 게시글 좋아요 캐시 정보 - 값 : 유저 아이디
            Set<Object> setValues = redisRepository.getSetValues(likeKey);
            // 좋아요 수 = 좋아요 유저 캐시 정보 수
            int likeCount = setValues.size();
            // 게시글 DB에 있는 좋아요 수와 캐시 좋아요 수가 다르다면 캐시의 수를 DB에 반영
            if (comment.getComment_like() != likeCount) {

                Set<Long> userIds = new HashSet<>();
                // 좋아요 취소한 건 어떻게 반영할지 ? 캐시에는 없고 DB에는 있는 것 삭제
                comment.getCommentLike().forEach(commentLike -> {
                    if (!redisRepository.isEmptySetValue(likeKey, commentLike.getUser().getId())) {
                        commentLike.updateLikeStatus();
                        comment.updateLikeCnt(commentLike.isLike_yn());
                    } else {
                        userIds.add(commentLike.getUser().getId());
                    }
                });

                // 캐시에 있는 유저아이디를 기준으로 게시글 좋아요 테이블에 추가
                setValues.forEach(setUserId -> {
                    long userId = Long.parseLong(setUserId.toString());

                    if (!userIds.contains(userId)) {
                        System.out.println("캐시에 데이터베이스로");

                        Optional<CommentLike> findCommentLike = commentLikeRepository.findByComment(comment, userId);

                        if (findCommentLike.isEmpty()) {
                            CommentLike commentLike = new CommentLike(comment, findUser(userId));
                            comment.addLikeList(commentLike);
                            commentLikeRepository.save(commentLike);
                        } else if (!findCommentLike.get().isLike_yn()) {
                            findCommentLike.get().changeLike();
                            comment.updateLikeCnt(findCommentLike.get().isLike_yn());
                        }
                    }
                });
            }

        });
    }

    private User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 유저는 존재하지 않습니다."));
    }

    private Board findBoard(long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 게시글은 존재하지 않습니다."));
    }

    private Comment findComment(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 댓글은 존재하지 않습니다."));
    }
}
