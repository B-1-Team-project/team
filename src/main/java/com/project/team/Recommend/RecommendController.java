package com.project.team.Recommend;

import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final SiteUserService siteUserService;

    private final RecommendService recommendService;

    private final RestaurantService restaurantService;

    @GetMapping("/weather")
    public String weatherMenu(Model model, Principal principal) {
        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());
            model.addAttribute("user", user);
        }
        return "weatherMenu";
    }

    @PostMapping("/weather")
    @ResponseBody
    public List<Object> weatherMenu(@RequestBody List<String> dataList, Principal principal, Model model) {
        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());
            model.addAttribute("user", user);
        }
        List<Recommend> recommendList;

        String rainValue = dataList.get(0);
        String tempValue = dataList.get(2);
        if (!"0".equals(rainValue)) {
            recommendList = this.recommendService.byRain(rainValue);
        } else {
            recommendList = this.recommendService.byTemp(tempValue);
        }
        List<Restaurant> restaurantList = this.restaurantService.byMain("%" + recommendList.get(0).getMenu() + "%", "%" + recommendList.get(1).getMenu() + "%",
                "%" + recommendList.get(2).getMenu() + "%");
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> restaurantMap = new HashMap<>();
            restaurantMap.put("id", String.valueOf(restaurantList.get(i).getId()));
            restaurantMap.put("name", restaurantList.get(i).getName());
            restaurantMap.put("image", restaurantList.get(i).getImage());
            restaurantMap.put("address", restaurantList.get(i).getAddress());
            mapList.add(restaurantMap);
        }
        List<Object> response = new ArrayList<>();
        response.add(recommendList);
        response.add(mapList);
        return response;
    }
}
