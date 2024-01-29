package com.project.trybargain.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatRequestDto {

    private long roomId;
    private String userId;
    private String userName;
    private String message;
    private ZonedDateTime time;
}
