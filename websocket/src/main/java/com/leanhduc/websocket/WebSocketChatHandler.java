package com.leanhduc.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ChatService chatService;
    ObjectMapper objectMapper;

    public WebSocketChatHandler(ChatService chatService, ObjectMapper objectMapper) {
        this.chatService = chatService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.chatService.add(session.getAttributes().get("userId").toString(), session);
        session.sendMessage(new TextMessage("Welcome! You are connected."));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        this.chatService.send(objectMapper.readValue(receivedMessage, ChatMessage.class));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.chatService.remove(session.getAttributes().get("token").toString());
        System.out.println("Disconnect");
    }
}
