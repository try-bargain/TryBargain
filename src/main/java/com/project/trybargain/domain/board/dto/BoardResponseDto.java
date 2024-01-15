package com.project.trybargain.domain.board.dto;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.entity.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private int price;
    private int board_like;
    private boolean active_yn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Category category;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.price = board.getPrice();
        this.board_like = board.getBoard_like();
        this.active_yn = board.isActive_yn();
        this.createdAt = board.getCreated_at();
        this.updatedAt = board.getUpdated_at();
        this.category = board.getCategory();
    }
}
