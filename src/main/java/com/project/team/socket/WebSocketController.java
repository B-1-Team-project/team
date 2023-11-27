package com.project.team.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatDto sendMessage(ChatDto chatDto) {
        return chatDto;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatDto addUser(ChatDto chatDto) {
        return chatDto;
    }
}
