package com.project.trybargain.domain.user.entity;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.chat.entity.ChattingMessage;
import com.project.trybargain.domain.chat.entity.ChattingRome;
import com.project.trybargain.domain.comment.entity.Comment;
import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.dto.UpdateMyPageRequestDto;
import com.project.trybargain.global.config.WebSecurityConfig;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private String user_id;

    @NotNull
    private String user_pw;

    @NotNull
    private String user_nm;

    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum user_role = UserRoleEnum.USER;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ChattingRome> chattingRome = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ChattingMessage> chattingMessages = new ArrayList<>();


    public User(JoinRequestDto requestDto) {
        this.user_id = requestDto.getUser_id();
        this.user_pw = requestDto.getPassword();
        this.user_nm = requestDto.getUser_nm();
        this.email = requestDto.getEmail();
    }

    public void addUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        userInfo.addUser(this);
    }

    public void addBoardList(Board board) {
        this.boardList.add(board);
    }
  
    public void update(UpdateMyPageRequestDto requestDto) {
        this.user_nm = requestDto.getUser_nm();
        this.email = requestDto.getEmail();
        this.userInfo.update(requestDto);
    }
}
