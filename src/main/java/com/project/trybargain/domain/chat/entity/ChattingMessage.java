package com.project.trybargain.domain.chat.entity;

import com.project.trybargain.domain.chat.dto.ChatRequestDto;
import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChattingMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChattingRoom chattingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime time;

    private String content;

    public ChattingMessage(User user, ChatRequestDto requestDto) {
        this.user = user;
        this.time = requestDto.getTime();
        this.content = requestDto.getMessage();
    }

    public void addChattingRoom(ChattingRoom chattingRoom) {
        this.chattingRoom = chattingRoom;
        chattingRoom.addMessage(this);
    }
}
