package com.project.team.map;

import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    private final SiteUserService siteUserService;
    private final RestaurantService restaurantService;

    @GetMapping("/view")
    public String search(Model model,
                         @RequestParam(value = "inputAddress", defaultValue = "aroundMe") String inputAddress,
                         Principal principal) {
        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());
            model.addAttribute("user", user);
        }
        model.addAttribute("inputAddress", inputAddress);

        List<Restaurant> restaurantList = restaurantService.getAll();
        model.addAttribute("resList", restaurantList);

        return "main";
    }
}
