package com.project.team.map;

import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import com.project.team.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    private final SiteUserService siteUserService;
    private final RestaurantService restaurantService;
    private final AlarmService alarmService;

    @GetMapping("/view")
    public String search(Model model,
                         @RequestParam(value = "inputAddress", defaultValue = "aroundMe") String inputAddress,
                         String lat, String lon,
                         Principal principal) {
        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());

            model.addAttribute("user", user);
            model.addAttribute("alarmList", alarmService.getByUser(user));
        }

        List<Restaurant> restaurantList = restaurantService.getAround(lon, lat, 0.005);

        for (Restaurant restaurant : restaurantList)
            this.restaurantService.saveAverageStar(restaurant);

        model.addAttribute("inputAddress", inputAddress);
        model.addAttribute("starTop3", this.restaurantService.top3AverageStar(restaurantList));
        model.addAttribute("reviewTop3", this.restaurantService.top3ReviewCount(restaurantList));
        model.addAttribute("resList", restaurantList);
        model.addAttribute("y", lat);
        model.addAttribute("x", lon);

        return "main";
    }

    @GetMapping("/findByKeyword")
    public String findByKeyword(@RequestParam String keyword, Model model, Principal principal) {
        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());
            model.addAttribute("user", user);
            model.addAttribute("alarmList", alarmService.getByUser(user));
        }


        List<Restaurant> searchResult = restaurantService.getRestaurantByKeyword(keyword);
        List<String> latList = new ArrayList<>();
        List<String> lonList = new ArrayList<>();
        // 각 음식점의 위치 정보를 가져와서 모델에 추가
        for (Restaurant restaurant : searchResult) {
            String lat = restaurant.getLocationX();
            String lon = restaurant.getLocationY();
            latList.add(lat);
            lonList.add(lon);
        }
        model.addAttribute("latList", latList);
        model.addAttribute("lonList", lonList);
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("inputAddress", "aroundMe");

        return "main";
    }
}
