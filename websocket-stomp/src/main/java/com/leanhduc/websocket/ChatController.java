package com.leanhduc.websocket;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller()
@RequestMapping("/token")
public class ChatController {

    @GetMapping()
    public ResponseEntity<String> generate() {
        var token = UUID.randomUUID().toString();
        return ResponseEntity.ok(token);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String handleGreeting(Map<String, String> payload, Principal principal) {
        System.out.println("Chat controller");
        System.out.println(principal);
        return "Hello, " + payload.get("name") + "!";
    }

    @MessageMapping("/group/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendToGroup(
            @DestinationVariable("roomId") String roomId,
            @Payload ChatMessage message,   // 👈 this is the payload
            Principal principal
    ) {
        return message; // Will be sent to /topic/room/{roomId}
    }
}
