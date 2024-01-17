package com.project.trybargain.domain.board.service;

import com.project.trybargain.domain.board.dto.BoardRequestDto;
import com.project.trybargain.domain.board.dto.BoardResponseDto;
import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.entity.Category;
import com.project.trybargain.domain.board.repository.BoardRepository;
import com.project.trybargain.domain.board.repository.CategoryRepository;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
}
