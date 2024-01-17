package com.project.trybargain.domain.board.entity;

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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    @ColumnDefault("true")
    private boolean use_yn;

    @OneToMany(mappedBy = "category")
    private List<Board> boardList = new ArrayList<>();

    public void addBoardList(Board board) {
        this.boardList.add(board);
        board.addCategory(this);
    }
}
