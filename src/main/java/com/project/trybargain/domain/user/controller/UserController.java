package com.project.trybargain.domain.user.controller;

import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.service.UserService;
import com.project.trybargain.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<MessageResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {
        return userService.join(joinRequestDto);

    }
}
