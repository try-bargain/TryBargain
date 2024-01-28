package com.project.trybargain.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * sockJS Fallback을 이용해 노출할 endpoint 설정
     * webSocket을 지원하지 않는 브라우저에서 http의 polling과 같은 방식으로 websocket의 요청을 수행하도록 도와준다.
     * sockJS를 사용할 경우, 요청을 보낼 때 - 설정한 endpoint/websocket 으로 작동한다.
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹 소켓이 handshake를 하기 위해 연결하는 endpoint
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버 -> 클라이언트로 발행하는 메시지에 대한 endpoint 설정 : 구독
//        registry.enableSimpleBroker("/sub");
        registry.enableSimpleBroker("/topic", "/queue");

        // 클라이언트 -> 서버로 발행하는 메시지에 대한 endpoint 설정 : 구독에 대한 메시지
//        registry.setApplicationDestinationPrefixes("/pub");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
