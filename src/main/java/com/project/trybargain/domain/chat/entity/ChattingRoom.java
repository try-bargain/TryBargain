package com.project.trybargain.domain.chat.entity;


import com.project.trybargain.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ChattingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(mappedBy = "chattingRoom")
    private List<ChattingMessage> chattingMessage = new ArrayList<>();

    public ChattingRoom(User seller, User buyer) {
        this.seller = seller;
        this.buyer = buyer;
    }

    public void addMessage(ChattingMessage message) {
        this.chattingMessage.add(message);
    }
}
