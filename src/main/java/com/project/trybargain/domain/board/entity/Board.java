package com.project.trybargain.domain.board.entity;

import com.project.trybargain.domain.board.dto.BoardRequestDto;
import com.project.trybargain.domain.user.User;
import com.project.trybargain.global.entity.TimeStamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
public class Board extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private int price;

    @NotNull
    private int board_like;

    @NotNull
    @ColumnDefault("true")
    private boolean active_yn;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private BoardStatusEnum boardStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.price = requestDto.getPrice();
    }
}

