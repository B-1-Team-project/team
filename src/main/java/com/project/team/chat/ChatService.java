package com.project.team.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void create(ChatDto chatDto) {
        Chat chat = new Chat();
        chat.setWriter(chatDto.getWriter());
        chat.setTarget(chatDto.getTarget());
        chat.setContent(chatDto.getContent());
        chat.setRoom(chatDto.getRoom());
        chat.setCreateDate(LocalDateTime.now());
        this.chatRepository.save(chat);
    }

    public List<Chat> getByRoom(String room) {
        return this.chatRepository.findByRoom(room);
    }

    public String getByTarget(String room, String target) {
        List<Chat> chats = this.chatRepository.findByRoomAndTarget(room, target);
        if (chats.isEmpty()) return null;
        return chats.get(0).getWriter();
    }
}
