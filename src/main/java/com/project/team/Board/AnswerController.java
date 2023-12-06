package com.project.team.Board;

import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final PostService postService;
    private final AnswerService answerService;
    private final SiteUserService siteUserService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal){
        Post post = this.postService.getPost(id);
        Answer answer = this.answerService.getAnswer(id);
        SiteUser user = siteUserService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("post", post);
            model.addAttribute("answer", answer);
            return "postDetail";
        }
        this.answerService.create(post,answerForm.getContent(),user);

        return String.format("redirect:/user/post/detail/%s", id);
    }

}
