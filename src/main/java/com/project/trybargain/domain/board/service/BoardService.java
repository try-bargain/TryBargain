package com.project.trybargain.domain.board.service;

import com.project.trybargain.domain.board.dto.BoardDetailResponseDto;
import com.project.trybargain.domain.board.dto.BoardRequestDto;
import com.project.trybargain.domain.board.dto.BoardResponseDto;
import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.entity.BoardLike;
import com.project.trybargain.domain.board.entity.BoardStatusEnum;
import com.project.trybargain.domain.board.entity.Category;
import com.project.trybargain.domain.board.repository.BoardLikeRepository;
import com.project.trybargain.domain.board.repository.BoardRepository;
import com.project.trybargain.domain.board.repository.CategoryRepository;
import com.project.trybargain.domain.comment.dto.CommentResponseDto;
import com.project.trybargain.domain.comment.entity.Comment;
import com.project.trybargain.domain.comment.repository.CommentRepository;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.entity.UserRoleEnum;
import com.project.trybargain.domain.user.repository.UserRepository;
import com.project.trybargain.global.dto.MessageResponseDto;
import com.project.trybargain.global.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final RedisRepository redisRepository;
    private final CommentRepository commentRepository;

    // 게시글 등록
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        User selectUser = findUser(user.getId());

        Board board = new Board(requestDto);
        board.addUser(selectUser);

        Category category = findCategory(requestDto.getCategoryId());
        category.addBoardList(board);

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    // 조회 상위 10개 - 추후 무한 스크롤링으로 처리 예정
    public Page<BoardResponseDto> getBoards(Pageable pageable) {
        List<BoardResponseDto> list = boardRepository.findAll(pageable).stream().map(BoardResponseDto::new).toList();
        long totalElements = boardRepository.countBoards();

        for (BoardResponseDto boardResponseDto : list) {
            Set<Object> boardLikeList = redisRepository.getSetValues("board:like:" + boardResponseDto.getId());
            if (!boardLikeList.isEmpty()) {
                boardResponseDto.setBoardLike(boardLikeList.size());
            }
        }

        return new PageImpl<>(list, pageable, totalElements);
    }

    // 게시물 검색
    public Page<BoardResponseDto> searchBoards(String query, Pageable pageable) {
        List<BoardResponseDto> list = boardRepository.findAllByTitle(query, pageable).stream().map(BoardResponseDto::new).toList();
        long totalElements = boardRepository.countSearchBoards(query);

        for (BoardResponseDto boardResponseDto : list) {
            Set<Object> boardLikeList = redisRepository.getSetValues("board:like:" + boardResponseDto.getId());
            if (!boardLikeList.isEmpty()) {
                boardResponseDto.setBoardLike(boardLikeList.size());
            }
        }

        return new PageImpl<>(list, pageable, totalElements);
    }

    // 게시글 상세
    public BoardDetailResponseDto getBoard(long id) {
        Board board = findBoard(id);

        BoardDetailResponseDto boardDetailResponseDto = new BoardDetailResponseDto(board);

        List<Comment> comments = commentRepository.findByBoardId(board.getId());
        List<CommentResponseDto> commentResponseDto = comments.stream()
                .map(CommentResponseDto::new)
                .toList();

        for (CommentResponseDto comment : commentResponseDto) {
            Set<Object> commentLikeList = redisRepository.getSetValues("comment:like:" + comment.getCommentId());
            if (!commentLikeList.isEmpty()) {
                comment.setLikeCnt(commentLikeList.size());
            }
            boardDetailResponseDto.commentListAdd(comment);
        }

        Set<Object> boardLikeList = redisRepository.getSetValues("board:like:" + board.getId());
        if (!boardLikeList.isEmpty()) {
            boardDetailResponseDto.setBoardLike(boardLikeList.size());
        }

        return boardDetailResponseDto;
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(long id, BoardRequestDto requestDto, User user) {
        Board board = findBoard(id);

        checkUser(board, user);
        board.update(requestDto);

        return new BoardResponseDto(board);
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<MessageResponseDto> deleteBoard(long id, User user) {
        Board board = findBoard(id);

        checkUser(board, user);
        board.delete();

        MessageResponseDto responseEntity = new MessageResponseDto("게시글 삭제를 성공하였습니다.",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    // 게시글 좋아요
//    @Transactional
    public ResponseEntity<MessageResponseDto> likeBoard(long id, long userId) {
        Board board = findBoard(id);
        User user = findUser(userId);

        if (redisRepository.isEmptySetValue("board:like:" + board.getId(), user.getId())) {
            redisRepository.deleteSetValue("board:like:" + board.getId(), user.getId());
            MessageResponseDto responseEntity = new MessageResponseDto("게시글 좋아요를 취소하였습니다.",200);
            return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
        }

        redisRepository.saveSet("board:like:" + board.getId(), user.getId());
        MessageResponseDto responseEntity = new MessageResponseDto("게시글 좋아요를 성공하였습니다.",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);

//        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board).orElse(null);
//        if (boardLike == null) {
//            boardLike = new BoardLike(user, board);
//            boardLikeRepository.save(boardLike);
//        }
//
//        if (boardLike.isLike_yn()) {
//            board.changeLike(board.getBoard_like() - 1);
//            boardLike.changeLike();
//            MessageResponseDto responseEntity = new MessageResponseDto("게시글 좋아요를 취소하였습니다.",200);
//            return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
//        }
//
//        board.changeLike(board.getBoard_like() + 1);
//        boardLike.changeLike();
//        MessageResponseDto responseEntity = new MessageResponseDto("게시글 좋아요를 성공하였습니다.",200);
//        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    // 게시글 상태 변경
    @Transactional
    public ResponseEntity<MessageResponseDto> statusChangeBoard(long id, BoardRequestDto requestDto, User user) {
        Board board = findBoard(id);

        checkUser(board, user);
        if (requestDto.getStatus().equals("ING")) {
            board.changeStatus(BoardStatusEnum.ING);
        }
        if (requestDto.getStatus().equals("COMP")) {
            board.changeStatus(BoardStatusEnum.COMP);
        }
        if (requestDto.getStatus().equals("CANCEL")) {
            board.changeStatus(BoardStatusEnum.CANCEL);
        }

        MessageResponseDto responseEntity = new MessageResponseDto("게시글 상태 변경을 성공하였습니다.",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    // 유저 검증
    private User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 유저는 존재하지 않습니다."));
    }

    // 카테고리 검증
    private Category findCategory(long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 카테고리는 존재하지 않습니다."));
    }

    // 게시글 검증
    private Board findBoard(long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 게시글은 존재하지 않습니다."));
    }

    private void checkUser(Board board, User user) {
        if (board.getUser().getId() != user.getId() && user.getUser_role().equals(UserRoleEnum.USER)) {
            throw new RuntimeException("해당 글의 작성자가 아닙니다.");
        }
    }
}
