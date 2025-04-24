package com.leanhduc.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatService chatService;
    private final ObjectMapper objectMapper;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler(), "/ws-chat").setAllowedOrigins("*").addInterceptors(handshakeInterceptor());
    }

    public WebSocketHandler handler() {
        return new WebSocketChatHandler(chatService, objectMapper);
    }

    public HandshakeInterceptor handshakeInterceptor() {
        return new WebSocketHandlerInterceptor(chatService);
    }

}
