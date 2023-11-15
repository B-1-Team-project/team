package com.project.team;

import com.project.team.User.UserCreateForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(UserCreateForm userCreateForm) {
        return "start";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("inputAddress", "aroundMe");
        return "main";
    }

    @GetMapping("/test")
    public String testPage() {
        return "test";
    }
}
