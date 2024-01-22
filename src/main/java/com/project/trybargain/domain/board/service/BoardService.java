package com.project.trybargain.domain.board.service;

import com.project.trybargain.domain.board.dto.BoardDetailResponseDto;
import com.project.trybargain.domain.board.dto.BoardRequestDto;
import com.project.trybargain.domain.board.dto.BoardResponseDto;
import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.entity.Category;
import com.project.trybargain.domain.board.repository.BoardRepository;
import com.project.trybargain.domain.board.repository.CategoryRepository;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.entity.UserRoleEnum;
import com.project.trybargain.domain.user.repository.UserRepository;
import com.project.trybargain.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

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

    // 조회 상위 100개 - 추후 무한 스크롤링으로 처리 예정
    public List<BoardResponseDto> getBoards() {
        return boardRepository.findAllByTop100().stream().map(BoardResponseDto::new).toList();
    }

    // 게시물 검색
    public List<BoardResponseDto> searchBoards(String query) {
        return boardRepository.findAllByTitle(query).stream().map(BoardResponseDto::new).toList();
    }

    // 게시글 상세
    public BoardDetailResponseDto getBoard(long id) {
        Board board = findBoard(id);
        return new BoardDetailResponseDto(board);
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(long id, BoardRequestDto requestDto, User user) {
        Board board = findBoard(id);

        if (board.getUser().getId() != user.getId() && user.getUser_role().equals(UserRoleEnum.USER)) {
            throw new RuntimeException("해당 글의 작성자가 아닙니다.");
        }
        board.update(requestDto);

        return new BoardResponseDto(board);
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<MessageResponseDto> deleteBoard(long id, User user) {
        Board board = findBoard(id);

        if (board.getUser().getId() != user.getId() && user.getUser_role().equals(UserRoleEnum.USER)) {
            throw new RuntimeException("해당 글의 작성자가 아닙니다.");
        }
        board.delete();

        MessageResponseDto responseEntity = new MessageResponseDto("게시글 삭제를 성공하였습니다.",200);
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
}
