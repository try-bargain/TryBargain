package com.project.trybargain.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.dto.UpdateMyPageRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String htel;

    private String birth;

    private int sex_cd;

    private String post;

    private String address1;

    private String address2;

    public UserInfo(JoinRequestDto requestDto) {
        this.htel = requestDto.getHtel();
        this.birth = requestDto.getBirth();
        this.sex_cd = requestDto.getSex_cd();
        this.post = requestDto.getPost();
        this.address1 = requestDto.getAddress1();
        this.address2 = requestDto.getAddress2();
    }

    public void update(UpdateMyPageRequestDto requestDto) {
        this.htel = requestDto.getHtel();
        this.birth = requestDto.getBirth();
        this.sex_cd = requestDto.getSex_cd();
        this.post = requestDto.getPost();
        this.address1 = requestDto.getAddress1();
        this.address2 = requestDto.getAddress2();
    }
}
