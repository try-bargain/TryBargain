package com.project.trybargain.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.trybargain.domain.user.dto.JoinRequestDto;
import com.project.trybargain.domain.user.dto.UpdateMyPageRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;

@Entity
@Getter
@NoArgsConstructor
public class UserInfo {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

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

    public void addUser(User user) {
        this.user = user;
    }
}
