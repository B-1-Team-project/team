package com.project.team.Post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model){
        List<Post> postList = postService.getList();
        model.addAttribute("postList", postList);
        return "board";
    }

    @GetMapping("/createPost")
    public String createPost(PostForm postForm){
        return "createPost";
    }

    @PostMapping("/createPost")
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "createPost";
        }
        postForm.setUser(principal.getName());
        this.postService.createPost(postForm.getTitle(), postForm.getContent(),postForm.getUser());
        return "redirect:/post/list";
    }



    @GetMapping("/detail/{id}")
    public String postDetail(Model model, @PathVariable("id") Integer id){
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "postDetail";
    }
}