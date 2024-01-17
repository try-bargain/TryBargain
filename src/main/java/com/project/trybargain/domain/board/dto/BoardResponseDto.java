package com.project.trybargain.domain.board.dto;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.entity.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private int price;
    private int board_like;
    private boolean active_yn;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Category category;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.price = board.getPrice();
        this.board_like = board.getBoard_like();
        this.active_yn = board.isActive_yn();
        this.created_at = board.getCreated_at();
        this.updated_at = board.getUpdated_at();
        this.category = board.getCategory();
    }
}
