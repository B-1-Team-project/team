package com.project.team.Restaurant;

import com.project.team.DataNotFoundException;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final SiteUserService siteUserService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public String register() {
        return "registerForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String register(Principal principal, @Valid RestaurantRegisterForm restaurantRegisterForm, BindingResult bindingResult) {
        SiteUser user = this.siteUserService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            return "registerForm";
        }
        this.restaurantService.registerRestaurant(restaurantRegisterForm.getName(), restaurantRegisterForm.getAddress(),
                restaurantRegisterForm.getNumber(), user);
        return "redirect:/map";
    }

    @GetMapping("/restaurant/{id}")
    public String reserve(Model model,@PathVariable("id") Integer id, BindingResult bindingResult, Principal principal) {

        if (id == null) {
            throw new DataNotFoundException("음식점 ID가 필요합니다.");
        }

        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            return "redirect:/form_errors";
        }

        String userId = principal.getName();
        SiteUser siteUser = siteUserService.getUser(userId);

        if (siteUser != null) {
            model.addAttribute("siteUser", siteUser);
        }

        return "reserve";


    }
}
