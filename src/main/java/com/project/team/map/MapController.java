package com.project.team.map;

import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import com.project.team.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/findByKeyword/{keyword}")
    public String findByKeyword(@PathVariable("keyword") String keyword, Model model, Principal principal) {

        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());
            model.addAttribute("user", user);
            model.addAttribute("alarmList", alarmService.getByUser(user));
        }

        List<Restaurant> searchResult = restaurantService.getRestaurantByKeyword(keyword);

        String lat = searchResult.get(0).getLocationY();
        String lon = searchResult.get(0).getLocationX();
        model.addAttribute("resList", searchResult);
        model.addAttribute("inputAddress", "searchKeyword");
        model.addAttribute("y", lat);
        model.addAttribute("x", lon);

        return "main";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<SearchDataDto> search(@RequestParam String keyword){
        List<Restaurant> searchList = restaurantService.getRestaurantByKeyword(keyword);
        List<SearchDataDto> searchDataDtoList = new ArrayList<>();
        for (Restaurant res : searchList) {
            SearchDataDto searchDataDto = new SearchDataDto();
            searchDataDto.setRestaurantName(res.getName());
            searchDataDtoList.add(searchDataDto);
        }
        return searchDataDtoList;

    }
}
