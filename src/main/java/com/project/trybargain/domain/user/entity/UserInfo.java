package com.project.trybargain.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserInfo {


    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @GeneratedValue
    private User user;

    private String htel;

    private String birth;

    private int sex_cd;

    private String post;

    private String address1;

    private String address2;

    public UserInfo(String htel, String birth, int sex_cd, String post, String address1, String address2) {
        this.htel = htel;
        this.birth = birth;
        this.sex_cd = sex_cd;
        this.post = post;
        this.address1 = address1;
        this.address2 = address2;
    }

    public void addUser(User user) {
        this.user = user;
    }
}
