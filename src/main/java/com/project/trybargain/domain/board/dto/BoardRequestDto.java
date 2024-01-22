package com.project.trybargain.domain.board.dto;

import com.project.trybargain.domain.board.entity.BoardStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private String contents;
    private int price;
    private long categoryId;
    private String status;
}
