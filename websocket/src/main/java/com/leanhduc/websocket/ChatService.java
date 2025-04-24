package com.leanhduc.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {
    private final Map<String, List<WebSocketSession>> userSessions;
    private final ObjectMapper objectMapper;

    public ChatService(ObjectMapper objectMapper) {
        this.userSessions = new ConcurrentHashMap<>();
        this.objectMapper = new ObjectMapper();
    }

    void add(String userId, WebSocketSession wsSession) {
        List<WebSocketSession> sessions = this.userSessions.get(userId);
        if (sessions == null) {
            this.userSessions.computeIfAbsent(userId, s -> Collections.synchronizedList(new ArrayList<>())).add(wsSession);
        } else {
            sessions.add(wsSession);
        }
    }

    void send(ChatMessage message) {
        List<WebSocketSession> sessions = this.userSessions.get(message.to);
        if (sessions != null) {
            sessions.forEach(webSocketSession -> {
                try {
                    String jsonResponse = objectMapper.writeValueAsString(message);
                    webSocketSession.sendMessage(new TextMessage(jsonResponse));
                } catch (Exception e) {
                }
            });
        }
    }

    void remove(String key) {
        this.userSessions.remove(key);
    }
}
