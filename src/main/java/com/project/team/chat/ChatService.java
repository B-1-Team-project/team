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
        SiteUser writer = userService.getUser(chatDto.getWriter());
        SiteUser target = userService.getUser(chatDto.getTarget());
        chat.setWriter(writer);
        chat.setTarget(target);
        chat.setContent(chatDto.getContent());
        chat.setRoom(chatDto.getRoom());
        chat.setRestaurant(restaurantService.getRestaurant(chatDto.getRestaurant()));
        chat.setCreateDate(LocalDateTime.now());
        chat.setType(chatDto.getType());
        chat.setConfirm(false);
        this.chatRepository.save(chat);
    }

    public List<Chat> getByRoom(String room) {
        return this.chatRepository.findByRoomAndType(room, "chat");
    }

    public SiteUser getByTarget(String room, SiteUser user) {
        List<Chat> info = this.chatRepository.findByRoomAndType(room, "info");
        if (info.get(0).getWriter().getLoginId().equals(user.getLoginId())) return info.get(0).getTarget();
        else if (info.get(0).getTarget().getLoginId().equals(user.getLoginId())) return info.get(0).getWriter();
        else return null;
    }

    public List<Chat> getRoomList(SiteUser user) {
        return chatRepository.findChatRoom(user);
    }

    public void delete(String room) {
        chatRepository.deleteAll(chatRepository.findByRoom(room));
    }

    public List<Chat> getInfo(String room) {
        return chatRepository.findByRoomAndType(room, "info");
    }

    public void setConfirm(String room, SiteUser user, Boolean confirm) {
        Chat chat = chatRepository.findByRoomAndTypeAndWriter(room, "info", user).get(0);
        chat.setConfirm(confirm);
        chatRepository.save(chat);
    }
}