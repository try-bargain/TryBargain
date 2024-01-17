package com.project.trybargain.domain.board.service;

import com.project.trybargain.domain.board.dto.BoardRequestDto;
import com.project.trybargain.domain.board.dto.BoardResponseDto;
import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.entity.Category;
import com.project.trybargain.domain.board.repository.BoardRepository;
import com.project.trybargain.domain.board.repository.CategoryRepository;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.entity.UserInfo;
import com.project.trybargain.domain.user.entity.UserRoleEnum;
import com.project.trybargain.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto);
        board.addUser(user);

        Category category = findCategory(requestDto.getCategoryId());
        category.addBoardList(board);

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    private User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 유저는 존재하지 않습니다."));
    }

    private Category findCategory(long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 카테고리는 존재하지 않습니다."));
    }
}
