package com.project.trybargain.domain.chat.service;

import com.project.trybargain.domain.chat.dto.ChatRequestDto;
import com.project.trybargain.domain.chat.entity.ChattingRoom;
import com.project.trybargain.domain.chat.repository.ChatMessageRepository;
import com.project.trybargain.domain.chat.repository.ChatRoomRepository;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    // 특정 broker로 메시지를 전달
    private final SimpMessageSendingOperations template;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createRoom(ChatRequestDto requestDto, User user) {
        User seller = findUser(requestDto.getUserId());
        ChattingRoom chattingRome = new ChattingRoom(seller, user);

        chatRoomRepository.save(chattingRome);

        requestDto.setMessage(user.getUser_nm() + "님 입장!!");
        template.convertAndSend("/topic/chatroom/" + chattingRome.getId(), requestDto);
    }


    // 유저 검증
    private User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 유저는 존재하지 않습니다."));
    }
}