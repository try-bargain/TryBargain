package com.project.trybargain.domain.chat.service;

import com.project.trybargain.domain.chat.dto.ChatRequestDto;
import com.project.trybargain.domain.chat.dto.ChatRoomResponseDto;
import com.project.trybargain.domain.chat.entity.ChattingMessage;
import com.project.trybargain.domain.chat.entity.ChattingRoom;
import com.project.trybargain.domain.chat.repository.ChatMessageRepository;
import com.project.trybargain.domain.chat.repository.ChatRoomRepository;
import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public void saveMessage(ChatRequestDto requestDto) {
        ChattingRoom chattingRoom = findChattingRoom(requestDto.getRoomId());
        User user = findUser(requestDto.getUserId());

        ChattingMessage chattingMessage = new ChattingMessage(user, requestDto);
        chattingMessage.addChattingRoom(chattingRoom);
        chatMessageRepository.save(chattingMessage);

        requestDto.setWriter(user.getUser_nm());
        template.convertAndSend("/topic/chatroom/" + requestDto.getRoomId(), requestDto);
    }

    public List<ChatRoomResponseDto> getRooms(long userId) {
        User user = findUser(userId);

        List<ChatRoomResponseDto> chattingRoomList = user.getChatrooms().stream().map(ChatRoomResponseDto::new).toList();

        return chattingRoomList;
    }

    public ChatRoomResponseDto getRoom(long roomId) {
        ChattingRoom chattingRoom = findChattingRoom(roomId);

        return new ChatRoomResponseDto(chattingRoom);
    }

    // 유저 검증
    private User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 유저는 존재하지 않습니다."));
    }

    // 채팅방 검증
    private ChattingRoom findChattingRoom(long id) {
        return chatRoomRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 채팅방은 존재하지 않습니다."));
    }
}