package com.project.team.chat;

import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final SiteUserService userService;

    public void create(ChatDto chatDto) {
        Chat chat = new Chat();
        chat.setWriter(userService.getUser(chatDto.getWriter()));
        chat.setTarget(userService.getUser(chatDto.getTarget()));
        chat.setContent(chatDto.getContent());
        chat.setRoom(chatDto.getRoom());
        chat.setCreateDate(LocalDateTime.now());
        this.chatRepository.save(chat);
    }

    public List<Chat> getByRoom(String room) {
        return this.chatRepository.findByRoom(room);
    }

    public SiteUser getByTarget(String room, SiteUser target) {
        List<Chat> chats = this.chatRepository.findByRoomAndTarget(room, target);
        if (chats.isEmpty()) return null;
        return chats.get(0).getWriter();
    }
}
