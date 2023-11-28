package com.project.team.socket;

import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import com.project.team.alarm.AlarmService;
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
    private final AlarmService alarmService;
    private final SiteUserService userService;

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

    @MessageMapping("/chat/{ownerId}/{userId}")
    public void sendMessage(@DestinationVariable String ownerId, @DestinationVariable String userId, ChatDto chatDto) {
        SiteUser user = userService.getUser(userId);
        SiteUser owner = userService.getUser(ownerId);
        alarmService.create(owner, user, "chat");
        simpMessagingTemplate.convertAndSend("/topic/" + ownerId + "/" + userId, chatDto);
    }
}
