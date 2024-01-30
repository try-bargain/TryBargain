package com.project.trybargain.domain.user.dto;

import com.project.trybargain.global.security.ValidationGroups.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequestDto {

    @NotBlank
    @Size(min = 4, max = 15 , groups = SizeCheckGroup.class)
    @Pattern(message = "잘못된 userId입니다." , regexp = "^[A-Za-z0-9]+$", groups = PatternCheckGroup.class)
    private String user_id;
//    @NotBlank
//    @Size(min = 8, max = 20, groups = SizeCheckGroup.class)// 특수문자 , 대문자, 소문자 1개씩 포함 // 이건
//    @Pattern(message = "잘못된 password입니다." , regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]+$", groups = PatternCheckGroup.class)
    private String password;
    @NotBlank
    @Size(min = 2 , max = 10 , groups = SizeCheckGroup.class)
    @Pattern(message = "잘못된 nickname입니다. " , regexp = "^[a-z0-9A-Z가-힝]+$", groups = PatternCheckGroup.class)
    private String user_nm;
    @NotBlank
    @Pattern(message = "잘못된 email입니다. " , regexp = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,6}$" ,
            groups = PatternCheckGroup.class)
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
