package com.project.trybargain.domain.user;

import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.entity.UserInfo;
import com.project.trybargain.domain.user.entity.UserRoleEnum;
import com.project.trybargain.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Disabled
    public void 회원가입() throws Exception {


        //UserInfo userInfo = new UserInfo("010-1234-5678","19910315",1,"50505","juso1","juso2");

        //User user2 = new User("tset111","1234","test_nm","erwr@nvaer.com",UserRoleEnum.USER);
        //user2.addUserInfo(userInfo);
        //userRepository.save(user2);


    }
}