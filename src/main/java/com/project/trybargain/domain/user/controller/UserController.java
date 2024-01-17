package com.project.trybargain.domain.user.controller;

import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.dto.MyPageResponseDto;
import com.project.trybargain.domain.user.dto.UpdateMyPageRequestDto;
import com.project.trybargain.domain.user.service.UserService;
import com.project.trybargain.global.dto.MessageResponseDto;
import com.project.trybargain.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
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
    public ResponseEntity<MessageResponseDto> join(@RequestBody JoinRequestDto joinRequestDto) {
        return userService.join(joinRequestDto);
    }

    /**
     * 마이페이지 조회
     * @param userDetails
     * @return
     */
    @GetMapping("/mypage")
    @ResponseBody
    public MyPageResponseDto mypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getMyInfo(userDetails.getUser().getId());
    }

    /**
     * 마이페이지 수정
     * @param requestDto
     * @return
     */
    @PutMapping("/mypage")
    @ResponseBody
    public ResponseEntity<MessageResponseDto> mypageUpdate(@RequestBody UpdateMyPageRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updateUser(requestDto, userDetails.getUser().getId());
    }
}
