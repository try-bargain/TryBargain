package com.project.trybargain.domain.chat.controller;

import com.project.trybargain.domain.chat.dto.ChatRequestDto;
import com.project.trybargain.domain.chat.service.ChatService;
import com.project.trybargain.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
