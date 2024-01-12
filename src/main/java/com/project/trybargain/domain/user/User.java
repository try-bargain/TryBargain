package com.project.trybargain.domain.user;

import com.project.trybargain.domain.board.entity.Board;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String user_id;

    @NotNull
    private String user_pw;

    @NotNull
    private String user_nm;

    private String email;

    @NotNull
    private UserRoleEnum user_role;

    @OneToMany(mappedBy = "user")
    private List<Board> board = new ArrayList<>();
}
