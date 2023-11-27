package com.project.team.socket;

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

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SiteUserService userService;
    private final RestaurantService restaurantService;

    @GetMapping("/chat/toOwner/{id}")
    @PreAuthorize("isAuthenticated()")
    public String chatToOwner(@PathVariable(value = "id") Integer id, Model model, Principal principal) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        SiteUser user = userService.getUser(principal.getName());
        SiteUser owner = restaurant.getOwner();
        model.addAttribute("user", user);
        model.addAttribute("owner", owner);
        return "chat";
    }

    @GetMapping("/chat/toCustomer/{id}")
    @PreAuthorize("isAuthenticated()")
    public String chatToCustomer(@PathVariable(value = "id") Integer id, Model model, Principal principal) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        SiteUser user = userService.getUser(principal.getName());
        SiteUser owner = restaurant.getOwner();
        model.addAttribute("user", user);
        model.addAttribute("owner", owner);
        return "chat";
    }
}
