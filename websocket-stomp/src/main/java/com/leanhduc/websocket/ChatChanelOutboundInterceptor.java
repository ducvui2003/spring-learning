package com.leanhduc.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

public class ChatChanelOutboundInterceptor implements ChannelInterceptor {
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        System.out.println("Channel Outbound after send complete");
        ChannelInterceptor.super.afterSendCompletion(message, channel, sent, ex);
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("Channel Outbound pre send");
        return ChannelInterceptor.super.preSend(message, channel);
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("Channel Outbound post send");
        ChannelInterceptor.super.postSend(message, channel, sent);
    }

//    @Override
//    public boolean preReceive(MessageChannel channel) {
//        System.out.println("Channel Outbound pre receive");
//        return ChannelInterceptor.super.preReceive(channel);
//    }
//
//    @Override
//    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
//        System.out.println("Channel Outbound post receive");
//        return ChannelInterceptor.super.postReceive(message, channel);
//    }
//
//    @Override
//    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
//        System.out.println("Channel Outbound after receive complete");
//        ChannelInterceptor.super.afterReceiveCompletion(message, channel, ex);
//    }
}
