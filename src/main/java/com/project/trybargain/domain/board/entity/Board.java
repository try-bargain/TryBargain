package com.project.trybargain.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.trybargain.domain.board.dto.BoardRequestDto;
import com.project.trybargain.domain.comment.entity.Comment;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.global.entity.TimeStamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    private String contents;

    @NotNull
    private int price;

    @NotNull
    private int board_like;

    @NotNull
    @ColumnDefault("true")
    private boolean active_yn;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private BoardStatusEnum status = BoardStatusEnum.ING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

    @OneToOne(mappedBy = "board", fetch = FetchType.LAZY)
    private BoardLike boardLike;

    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.price = requestDto.getPrice();
    }

    public void addUser(User user) {
        this.user = user;
        user.addBoardList(this);
    }

    public void addCategory(Category category) {
        this.category = category;
    }
}

