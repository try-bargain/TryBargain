package com.project.trybargain.domain.board.dto;

import com.project.trybargain.domain.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private int price;
    private int boardLike;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CategoryResponseDto category;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.price = board.getPrice();
        this.boardLike = board.getBoard_like();
        this.active = board.isActive_yn();
        this.createdAt = board.getCreated_at();
        this.updatedAt = board.getUpdated_at();
        this.category = new CategoryResponseDto(board.getCategory());
    }
}
