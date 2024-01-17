package com.project.trybargain.domain.user.dto;

import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.entity.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class MyPageResponseDto {
    private String user_id;
    private String user_nm;
    private String email;

    private String htel;
    private String birth;
    private int sex_cd;
    private String post;
    private String address1;
    private String address2;

    public MyPageResponseDto(User user) {
        this.user_id = user.getUser_id();
        this.user_nm = user.getUser_nm();
        this.email = user.getEmail();
        this.htel = user.getUserInfo().getHtel();
        this.birth = user.getUserInfo().getBirth();
        this.sex_cd = user.getUserInfo().getSex_cd();
        this.post = user.getUserInfo().getPost();
        this.address1 = user.getUserInfo().getAddress1();
        this.address2 = user.getUserInfo().getAddress2();
    }


}
