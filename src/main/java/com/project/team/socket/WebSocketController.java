package com.project.team.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
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

    @MessageMapping("/chat/{resId}/{loginId}")
    public void sendMessage(@DestinationVariable Integer resId, @DestinationVariable String loginId, ChatDto chatDto) {
        simpMessagingTemplate.convertAndSend("/topic/" + resId + "/" + loginId, chatDto);
    }
}
