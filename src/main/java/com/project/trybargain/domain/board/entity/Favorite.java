package com.project.trybargain.domain.board.entity;

import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Favorite {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "favorite")
    private List<BoardFavorite> boardFavoriteList = new ArrayList<>();
}
