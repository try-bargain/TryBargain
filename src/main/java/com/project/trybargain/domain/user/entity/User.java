package com.project.trybargain.domain.user.entity;

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
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum user_role;

    @OneToMany(mappedBy = "user")
    private List<Board> board = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserInfo userInfo;

    public User(String user_id, String user_pw, String user_nm, String email, UserRoleEnum user_role) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_nm = user_nm;
        this.email = email;
        this.user_role = user_role;
    }

    public void addUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        userInfo.addUser(this);
    }
}
