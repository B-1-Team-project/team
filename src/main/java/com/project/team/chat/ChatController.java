package com.project.team.chat;

import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final SiteUserService userService;
    private final ChatService chatService;
    private final RestaurantService restaurantService;

    @GetMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String create(@PathVariable("id") Integer id, Principal principal) {
        String room = UUID.randomUUID().toString();
        Restaurant restaurant = restaurantService.getRestaurant(id);
        chatService.create(new ChatDto("채팅방이 개설되었습니다", restaurant.getOwner().getLoginId(),
                principal.getName(), room, restaurant.getId(), "info"));
        return "redirect:/chat/join/" + room;
    }

    @GetMapping("/join")
    @PreAuthorize("isAuthenticated()")
    public String join(Model model, Principal principal) {
        SiteUser user = userService.getUser(principal.getName());
        List<Chat> chatList = chatService.getRoomList(user);

        model.addAttribute("user", user);
        model.addAttribute("roomList", chatList);
        return "room";
    }

    @GetMapping("/join/{room}")
    @PreAuthorize("isAuthenticated()")
    public String join(@PathVariable(value = "room") String room, Model model, Principal principal) {
        chatService.setConfirm(room, true);
        SiteUser user = userService.getUser(principal.getName());
        SiteUser target = chatService.getByTarget(room, user);
        Restaurant restaurant = chatService.getInfo(room).get(0).getRestaurant();
        List<Chat> chatList = chatService.getRoomList(user);

        model.addAttribute("user", user);
        model.addAttribute("target", target);
        model.addAttribute("room", room);
        model.addAttribute("chatList", chatService.getByRoom(room));
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("roomList", chatList);
        return "chat";
    }

    @PostMapping("/delete/{room}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("room") String room) {
        this.chatService.delete(room);
        return "redirect:/";
    }
}
