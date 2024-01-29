package com.project.trybargain.domain.chat.controller;

import com.project.trybargain.domain.chat.dto.ChatRequestDto;
import com.project.trybargain.domain.chat.dto.ChatRoomResponseDto;
import com.project.trybargain.domain.chat.service.ChatService;
import com.project.trybargain.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chatRoom")
    public void createRoom(@RequestBody ChatRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        chatService.createRoom(requestDto, userDetails.getUser());
    }

    @MessageMapping("/sendMessage") // 메시지 밸행, url 앞에 /pub 생략
    public void sendMessage(@Payload ChatRequestDto requestDto) {
        chatService.saveMessage(requestDto);
    }

    @GetMapping("/chatRooms")
    public List<ChatRoomResponseDto> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.getRooms(userDetails.getUser().getId());
    }

    @GetMapping("/chatRoom/{id}")
    public ChatRoomResponseDto getRoom(@PathVariable long id) {
        return chatService.getRoom(id);
    }
}
