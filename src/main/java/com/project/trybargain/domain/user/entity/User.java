package com.project.trybargain.domain.user.entity;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.chat.entity.ChattingRoom;
import com.project.trybargain.domain.comment.entity.Comment;
import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.dto.UpdateMyPageRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userinfo_id")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<ChattingRoom> sellerChattingRome = new ArrayList<>();

    @OneToMany(mappedBy = "buyer")
    private List<ChattingRoom> buyerChattingRome = new ArrayList<>();

    public User(JoinRequestDto requestDto, UserInfo userInfo) {
        this.user_id = requestDto.getUser_id();
        this.user_pw = requestDto.getPassword();
        this.user_nm = requestDto.getUser_nm();
        this.email = requestDto.getEmail();
        this.userInfo = userInfo;
    }

    public void addComment(Comment comment) {
        this.commentList.add((comment));
    }

    public void addBoardList(Board board) {
        this.boardList.add(board);
    }
  
    public void update(UpdateMyPageRequestDto requestDto) {
        this.user_nm = requestDto.getUser_nm();
        this.email = requestDto.getEmail();
        this.userInfo.update(requestDto);
    }

    public List<ChattingRoom> getChatrooms() {
        List<ChattingRoom> chattingRoomList = new ArrayList<>(this.sellerChattingRome);
        chattingRoomList.addAll(this.buyerChattingRome);
        return chattingRoomList;
    }
}
