package com.project.team.chat;

import com.project.team.Restaurant.RestaurantService;
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
    private final RestaurantService restaurantService;

    public void create(ChatDto chatDto) {
        Chat chat = new Chat();
        chat.setWriter(userService.getUser(chatDto.getWriter()));
        chat.setTarget(userService.getUser(chatDto.getTarget()));
        chat.setContent(chatDto.getContent());
        chat.setRoom(chatDto.getRoom());
        chat.setRestaurant(restaurantService.getRestaurant(chatDto.getRestaurant()));
        chat.setCreateDate(LocalDateTime.now());
        this.chatRepository.save(chat);
    }

    public List<Chat> getByRoom(String room) {
        return this.chatRepository.findByRoom(room);
    }

    public SiteUser getByTarget(String room, SiteUser user) {
        List<Chat> chats = this.chatRepository.findByRoom(room);
        for (Chat chat : chats) {
            if (chat.getWriter().getLoginId().equals(user.getLoginId())) return chat.getTarget();
            else if (chat.getTarget().getLoginId().equals(user.getLoginId())) return chat.getWriter();
        }
        return null;
    }

    public List<Chat> getRoomList(SiteUser user) {
        return chatRepository.findChatRoom(user);
    }

    public void delete(String room) {
        chatRepository.deleteAll(chatRepository.findByRoom(room));
    }
}
