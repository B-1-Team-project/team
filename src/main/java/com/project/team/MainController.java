package com.project.team;


import com.project.team.User.SiteUserService;
import com.project.team.User.UserCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final SiteUserService siteUserService;

    @GetMapping("/")
    public String index(UserCreateForm userCreateForm) {
        return "start";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "redirect:/process";
    }

    @GetMapping("/process")
    public String interProcess() {
        return "interprocess";
    }

}
