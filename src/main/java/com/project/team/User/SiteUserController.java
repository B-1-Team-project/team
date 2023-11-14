package com.project.team.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class SiteUserController {
    private final SiteUserService siteUserService;

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "start";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "start";
        }

        try{
            siteUserService.create(userCreateForm.getLoginId(), userCreateForm.getPassword1(), userCreateForm.getUsername(), userCreateForm.getEmail());

        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("signupFaild", "이미 등록된 사용자 입니다.");
            return "start";
        }catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("signupFaild", e.getMessage());
            return "start";
        }
        return "redirect:/";
    }
}
