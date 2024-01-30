package com.project.trybargain.domain.chat.dto;

import com.project.trybargain.domain.chat.entity.ChattingMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {

    private String userId;
    private String userName;
    private String message;

    public ChatMessageResponseDto(ChattingMessage chattingMessage) {
        this.userId = chattingMessage.getUser().getUser_id();
        this.userName = chattingMessage.getUser().getUser_nm();
        this.message = chattingMessage.getContent();
    }
}
