package com.project.trybargain.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
    private Board board;
}
