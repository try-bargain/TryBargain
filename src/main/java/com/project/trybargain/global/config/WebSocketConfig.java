package com.project.trybargain.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹 소켓이 handshake를 하기 위해 연결하는 endpoint
        // sockJS Fallback 옵션 활성화
        // sockJS를 사용할 경우, 요청을 보낼 때 - 설정한 endpoint/websocket 으로 작동한다.
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 웹소켓을 통해 보내진 메시지를 가저오기 위한 주소 설정
        registry.enableSimpleBroker("/topic");

        // 클라이언트에서 서버로 메시지를 보내기 위한 주소 설정
        registry.setApplicationDestinationPrefixes("/app");
    }
}
