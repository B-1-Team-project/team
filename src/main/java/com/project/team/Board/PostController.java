package com.project.team.Board;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/board")
    public String board(Model model){
        List<Post> postList = postService.postList();
        model.addAttribute("postList", postList);
        return "board";
    }

    @GetMapping("/board/createPost")
    public String createPost(PostForm postForm){
        return "createPost";
    }

    @PostMapping("/board/createPost")
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "createPost";
        }
        postForm.setUser(principal.getName());
        this.postService.createPost(postForm.getTitle(), postForm.getContent(),postForm.getUser());
        return "redirect:/user/board";
    }



    @GetMapping("/post/detail/{id}")
    public String postDetail(Model model, @PathVariable("id") Integer id){
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "postDetail";
    }
}