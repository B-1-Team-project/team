package com.project.team.Board.Answer;

import com.project.team.Board.Post.Post;
import com.project.team.Board.Post.PostService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final PostService postService;
    private final AnswerService answerService;
    private final SiteUserService siteUserService;

    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String create(@PathVariable("id") Integer id, Principal principal, String content){
        Post post = this.postService.getPost(id);
        SiteUser user = this.siteUserService.getUser(principal.getName());
        this.answerService.createAnswer(post, content, user);
        return String.format("redirect:/post/detail/%s", id);
    }

    @PostMapping("/create/inList/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createInList(@PathVariable("id") Integer id, Principal principal, String content) {
        Post post = this.postService.getPost(id);
        SiteUser user = this.siteUserService.getUser(principal.getName());
        this.answerService.createAnswer(post, content, user);
        return "redirect:/post/list";
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, Model model) {
        Answer answer = this.answerService.getAnswer(id);
        model.addAttribute("answer", answer);
        return "answerModify";
    }

    @GetMapping("/modify/inList/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyInList(@PathVariable("id") Integer id, Model model) {
        Answer answer = this.answerService.getAnswer(id);
        model.addAttribute("answer", answer);
        return "answerModify";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@PathVariable("id") Integer id, String content) {
        Answer answer = this.answerService.getAnswer(id);
        this.answerService.modifyAnswer(answer, content);
        return String.format("redirect:/post/detail/%s", answer.getPost().getId());
    }

    @PostMapping("/modify/inList/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyInList(@PathVariable("id") Integer id, String content) {
        Answer answer = this.answerService.getAnswer(id);
        this.answerService.modifyAnswer(answer, content);
        return "redirect:/post/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        this.answerService.deleteAnswer(answer);
        return String.format("redirect:/post/detail/%s", answer.getPost().getId());
    }

    @GetMapping("/delete/inList/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteInList(@PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        this.answerService.deleteAnswer(answer);
        return "redirect:/post/list";
    }
}
