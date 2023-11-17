package com.project.team;

import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import com.project.team.User.UserCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final RestaurantService restaurantService;
    private final SiteUserService siteUserService;

    @GetMapping("/")
    public String index(UserCreateForm userCreateForm) {
        return "start";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "redirect:/map/view";
    }

    @GetMapping("/regTest")
    public String regTest() {
        return "registerForm";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping(value = "/test/receive", method = RequestMethod.POST)
    public String receive(@RequestBody List<Map<String, String>> result) {
        SiteUser user = siteUserService.getUser("njk7740");
        for (Map<String, String> data : result) {
            Restaurant restaurant = restaurantService.registerRestaurant(
                    data.get("place_name"),
                    data.get("road_address_name"),
                    data.get("phone"),
                    null,
                    null,
                    user
            );
            restaurantService.setLocation(restaurant, data.get("y"), data.get("x"));
        }
        return "/main";
    }
}
