package com.project.team.chat;

import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import com.project.team.alarm.Alarm;
import com.project.team.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final SiteUserService userService;
    private final RestaurantService restaurantService;
    private final ChatService chatService;
    private final AlarmService alarmService;

    @GetMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String chatToOwner(@PathVariable(value = "id") Integer id, Model model, Principal principal) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        SiteUser user = userService.getUser(principal.getName());
        SiteUser owner = restaurant.getOwner();
        String room = UUID.randomUUID().toString();
        model.addAttribute("user", user);
        model.addAttribute("target", owner);
        model.addAttribute("room", room);
        return "chat";
    }

    @GetMapping("/join/{room}")
    @PreAuthorize("isAuthenticated()")
    public String chatToCustomer(@PathVariable(value = "room") String room, Model model, Principal principal) {
        SiteUser user = userService.getUser(principal.getName());
        SiteUser target = chatService.getByTarget(room, user);
        List<Alarm> alarmList = alarmService.getByRoom(user, room);

        for (Alarm alarm : alarmList) alarmService.confirm(alarm);

        model.addAttribute("user", user);
        model.addAttribute("target", target);
        model.addAttribute("room", room);
        model.addAttribute("chatList", chatService.getByRoom(room));
        return "chat";
    }
}
