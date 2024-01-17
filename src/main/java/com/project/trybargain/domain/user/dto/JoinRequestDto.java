package com.project.trybargain.domain.user.dto;

import com.project.trybargain.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class JoinRequestDto {

    private String user_id;
    private String password;
    private String user_nm;
    private String email;

    private String htel;
    private String birth;
    private int sex_cd;
    private String post;
    private String address1;
    private String address2;

    public void setPassword(String password) {
        this.password = password;
    }
}
