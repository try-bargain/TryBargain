package com.project.trybargain.domain.user.controller;

import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.dto.MyPageResponseDto;
import com.project.trybargain.domain.user.dto.UpdateMyPageRequestDto;
import com.project.trybargain.domain.user.service.UserService;
import com.project.trybargain.global.dto.MessageResponseDto;
import com.project.trybargain.global.security.UserDetailsImpl;
import com.project.trybargain.global.security.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * @param joinRequestDto
     * @return
     */
    @PostMapping("/auth/join")
    public ResponseEntity<MessageResponseDto> join(@Validated(ValidationGroups.ValidationSequence.class) @RequestBody JoinRequestDto joinRequestDto) {
        return userService.join(joinRequestDto);
    }

    /**
     * 마이페이지 조회
     * @param userDetails
     * @return
     */
    @GetMapping("/mypage")
    public MyPageResponseDto mypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getMyInfo(userDetails.getUser().getId());
    }

    /**
     * 마이페이지 수정
     * @param requestDto
     * @return
     */
    @PutMapping("/mypage")
    public ResponseEntity<MessageResponseDto> mypageUpdate(@RequestBody UpdateMyPageRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updateUser(requestDto, userDetails.getUser().getId());
    }

    @PostMapping("/auth/userIdCheck")
    public ResponseEntity<MessageResponseDto> duplicate(@RequestBody JoinRequestDto requestDto) {
        return userService.duplicate(requestDto);
    }

}
