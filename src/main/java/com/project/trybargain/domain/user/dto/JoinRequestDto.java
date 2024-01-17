package com.project.trybargain.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
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

}
