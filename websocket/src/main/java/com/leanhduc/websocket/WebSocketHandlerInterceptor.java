package com.leanhduc.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.StringTokenizer;

public class WebSocketHandlerInterceptor implements HandshakeInterceptor {
    ChatService chatService;

    public WebSocketHandlerInterceptor(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String userId = getValue(request.getURI().getQuery(), "userId");
        if (userId == null) {
            return false;
        }
        attributes.put("userId", userId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    String getValue(String query, String key) {
        StringTokenizer tokenizer = new StringTokenizer(query, "&");
        while (tokenizer.hasMoreTokens()) {
            String[] tokens = tokenizer.nextToken().split("=");
            String token = tokens[0];
            if (token.equals(key))
                return tokens[1];
        }
        return null;
    }
}
