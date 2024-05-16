package com.project.trybargain.domain.board.controller;

import com.project.trybargain.domain.board.dto.BoardDetailResponseDto;
import com.project.trybargain.domain.board.dto.BoardRequestDto;
import com.project.trybargain.domain.board.dto.BoardResponseDto;
import com.project.trybargain.domain.board.service.BoardService;
import com.project.trybargain.global.dto.MessageResponseDto;
import com.project.trybargain.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    @GetMapping("/board")
    public Page<BoardResponseDto> getBoards(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            @PageableDefault Pageable pageable) {
        return boardService.getBoards(pageable);
    }

    @GetMapping("/board/search")
    public List<BoardResponseDto> searchBoards(@RequestParam(value = "query") String query) {
        return boardService.searchBoards(query);
    }

    @GetMapping("/board/{id}")
    public BoardDetailResponseDto getBoard(@PathVariable long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<MessageResponseDto> deleteBoard(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }

    @PostMapping("/board/like/{id}")
    public ResponseEntity<MessageResponseDto> likeBoard(@PathVariable long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.likeBoard(id, userDetails.getUser().getId());
    }

    @PostMapping("/board/status/{id}")
    public ResponseEntity<MessageResponseDto> statusChangeBoard(@PathVariable long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.statusChangeBoard(id, requestDto, userDetails.getUser());
    }
}
