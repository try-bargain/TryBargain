package com.project.trybargain.domain.chat.dto;

import com.project.trybargain.domain.chat.entity.ChattingRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {

    private long roomId;
    private String sellerId;
    private String sellerName;
    private String buyerId;
    private String buyerName;

    public ChatRoomResponseDto(ChattingRoom chattingRome) {
        this.roomId = chattingRome.getId();
        this.sellerId = chattingRome.getSeller().getUser_id();
        this.sellerName = chattingRome.getSeller().getUser_nm();
        this.buyerId = chattingRome.getBuyer().getUser_id();
        this.buyerName = chattingRome.getBuyer().getUser_nm();
    }
}
