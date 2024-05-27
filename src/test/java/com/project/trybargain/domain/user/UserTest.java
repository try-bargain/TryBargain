package com.project.trybargain.domain.user;

import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.entity.UserInfo;
import com.project.trybargain.domain.user.entity.UserRoleEnum;
import com.project.trybargain.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("회원가입 - 성공")
    public void join() throws Exception {
        // GIVEN
        JoinRequestDto joinRequestDto = new JoinRequestDto();
        joinRequestDto.setUser_id("testid");
        joinRequestDto.setPassword("1234");
        joinRequestDto.setUser_nm("닉네임");
        joinRequestDto.setEmail("erwr@nvaer.com");
        joinRequestDto.setHtel("010-1234-5678");
        joinRequestDto.setBirth("19910315");
        joinRequestDto.setSex_cd(1);
        joinRequestDto.setPost("50505");
        joinRequestDto.setAddress1("juso1");
        joinRequestDto.setAddress2("juso2");
        UserInfo userInfo = new UserInfo(joinRequestDto);
        User user = new User(joinRequestDto, userInfo);

        // WHEN
        userRepository.save(user);

        // THEN
        em.flush();
        assertThat(user).isEqualTo(userRepository.findUserByUserId("testid").get());
    }

    @Test
    @DisplayName("회원가입 - 중복 회원 예외")
    public void join_exception() throws Exception {
        // given
        JoinRequestDto joinRequestDto = new JoinRequestDto();
        joinRequestDto.setUser_id("testid");
        joinRequestDto.setPassword("1234");
        joinRequestDto.setUser_nm("닉네임");
        joinRequestDto.setEmail("erwr@nvaer.com");
        joinRequestDto.setHtel("010-1234-5678");
        joinRequestDto.setBirth("19910315");
        joinRequestDto.setSex_cd(1);
        joinRequestDto.setPost("50505");
        joinRequestDto.setAddress1("juso1");
        joinRequestDto.setAddress2("juso2");
        UserInfo userInfo = new UserInfo(joinRequestDto);
        User user = new User(joinRequestDto, userInfo);
        User user2 = new User(joinRequestDto, userInfo);

        // when
        userRepository.save(user);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userRepository.save(user2));

        // then
        assertThat(e.getMessage()).isEqualTo("중복된 사용자가 존재합니다.");
    }
}