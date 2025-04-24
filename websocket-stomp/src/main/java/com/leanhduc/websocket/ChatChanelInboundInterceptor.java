package com.leanhduc.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;


public class ChatChanelInboundInterceptor implements ChannelInterceptor {
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        System.out.println("Channel Inbound after send complete");
        ChannelInterceptor.super.afterSendCompletion(message, channel, sent, ex);
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("Channel Inbound pre send");

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                accessor.setUser(new UsernamePasswordAuthenticationToken("ducvui2003", null, List.of()));
            }
        }
        return ChannelInterceptor.super.preSend(message, channel);
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("Channel Inbound post send");
        ChannelInterceptor.super.postSend(message, channel, sent);
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        System.out.println("Channel Inbound pre receive");
        return ChannelInterceptor.super.preReceive(channel);
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        System.out.println("Channel Inbound post receive");
        return ChannelInterceptor.super.postReceive(message, channel);
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        System.out.println("Channel Inbound after receive complete");
        ChannelInterceptor.super.afterReceiveCompletion(message, channel, ex);
    }
}
