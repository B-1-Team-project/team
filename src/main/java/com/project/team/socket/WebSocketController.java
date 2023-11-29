package com.project.team.socket;

import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import com.project.team.alarm.Alarm;
import com.project.team.alarm.AlarmService;
import com.project.team.chat.ChatDto;
import com.project.team.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AlarmService alarmService;
    private final SiteUserService userService;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(ChatDto chatDto) {
        chatService.create(chatDto);
        SiteUser writer = userService.getUser(chatDto.getWriter());
        SiteUser target = userService.getUser(chatDto.getTarget());
        String room = chatDto.getRoom();
        List<Alarm> alarmList = alarmService.get(target, writer, "chat", false);
        if (alarmList.isEmpty()) alarmService.create(target, writer, "chat", room);
        simpMessagingTemplate.convertAndSend("/topic/" + room, chatDto);
    }

    @MessageMapping("/main")
    public void mainEvent(String data) {
        simpMessagingTemplate.convertAndSend("/topic/main", data);
    }
}
