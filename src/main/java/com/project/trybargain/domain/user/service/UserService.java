package com.project.trybargain.domain.user.service;

import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.dto.MyPageResponseDto;
import com.project.trybargain.domain.user.dto.UpdateMyPageRequestDto;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.entity.UserInfo;
import com.project.trybargain.domain.user.repository.UserRepository;
import com.project.trybargain.global.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //회원 가입
    public ResponseEntity<MessageResponseDto> join(JoinRequestDto joinRequestDto) {
        validateCheckUser(joinRequestDto.getUser_id());

        String encodedPassword = passwordEncoder.encode(joinRequestDto.getPassword());
        joinRequestDto.setPassword(encodedPassword);

        UserInfo userInfo = new UserInfo(joinRequestDto);
        User user = new User(joinRequestDto);
        user.addUserInfo(userInfo);

        userRepository.save(user);
        MessageResponseDto responseEntity = new MessageResponseDto("join complete",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }


    //회원가입 검증
    private void validateCheckUser(String userId) {
        Optional<User> findUser = userRepository.findUserByUserId(userId);

        if(!findUser.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

    }

    //마이페이지 조회
    public MyPageResponseDto getMyInfo(long id) {
        User user = findUser(id);
        return new MyPageResponseDto(user);
    }

    //사용자 존재 체크
    private User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 유저는 존재하지 않습니다."));
    }

    public ResponseEntity<MessageResponseDto> updateUser(UpdateMyPageRequestDto requestDto, long id) {
        User updateUser = findUser(id);
        updateUser.update(requestDto);

        MessageResponseDto responseEntity = new MessageResponseDto("update complete",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }



}
