package com.project.trybargain.domain.user.service;

import com.project.trybargain.domain.user.dto.FindUserRequestDto;
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
        String encodedPassword = passwordEncoder.encode(joinRequestDto.getPassword());
        joinRequestDto.setPassword(encodedPassword);

        // 회원 중복 확인
        String userId = joinRequestDto.getUser_id();
        Optional<User> checkUsername = userRepository.findUserByUserId(userId);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 이메일 중복 확인
        String email = joinRequestDto.getEmail();
        Optional<User> checkEmail = userRepository.findUserByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        // 닉네임 중복 확인
        String nickname = joinRequestDto.getUser_nm();
        Optional<User> checkNickname = userRepository.findUserByName(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        UserInfo userInfo = new UserInfo(joinRequestDto);
        User user = new User(joinRequestDto);
        user.addUserInfo(userInfo);

        userRepository.save(user);
        MessageResponseDto responseEntity = new MessageResponseDto("join complete",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    //마이페이지 조회
    public MyPageResponseDto getMyInfo(long id) {
        User user = findUser(id);
        return new MyPageResponseDto(user);
    }

    // 회원 정보 수정
    public ResponseEntity<MessageResponseDto> updateUser(UpdateMyPageRequestDto requestDto, long id) {
        User updateUser = findUser(id);
        updateUser.update(requestDto);

        MessageResponseDto responseEntity = new MessageResponseDto("update complete",200);
        return ResponseEntity.status(HttpStatus.OK).body(responseEntity);
    }

    // 아이디 중복 확인
    public ResponseEntity<MessageResponseDto> duplicate(FindUserRequestDto requestDto) {
        Optional<User> user = userRepository.findUserByUserId(requestDto.getUserId());

        if(user.isEmpty()) {
            MessageResponseDto message = new MessageResponseDto("없는 userId 입니다. ", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body((message));
        }
        MessageResponseDto message = new MessageResponseDto("해당 userId가 이미 존재합니다. ", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body((message));
    }

    //사용자 존재 체크
    private User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 유저는 존재하지 않습니다."));
    }
}
