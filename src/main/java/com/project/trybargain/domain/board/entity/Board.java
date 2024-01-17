package com.project.trybargain.domain.board.entity;

import com.project.trybargain.domain.board.dto.BoardRequestDto;
import com.project.trybargain.domain.comment.entity.Comment;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.global.entity.TimeStamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private boolean active_yn = true;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private BoardStatusEnum status = BoardStatusEnum.ING;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<BoardLike> boardLikeList = new ArrayList<>();

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

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.price = requestDto.getPrice();
    }

    public void delete() {
        this.active_yn = false;
    }
}

