package com.project.team.Board;

import com.project.team.DataNotFoundException;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.maven.model.Site;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.model.IModel;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final AnswerService answerService;
    private final SiteUserService siteUserService;

    @GetMapping("/board")
    @PreAuthorize("isAuthenticated()")
    public String board(Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Post> paging = this.postService.getList(page);
        SiteUser user = this.siteUserService.getUser(principal.getName());
        model.addAttribute("paging", paging);
        model.addAttribute("user", user);
        return "board";
    }

    @GetMapping("/board/createPost")
    public String createPost(){
        return "createPost";
    }

    @PostMapping("/board/createPost")
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "createPost";
        }
        this.postService.createPost(postForm.getTitle(), postForm.getContent(), principal.getName());
        return "redirect:/user/board";
    }



    @GetMapping("/post/detail/{id}")
    public String postDetail(Model model, @PathVariable("id") Integer id){
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "postDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(Model model, Principal principal, PostForm postForm, @PathVariable("id") Integer id){
        Post post = this.postService.getPost(id);
        if(!post.getUser().getLoginId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        model.addAttribute("post", post);
        return "createPost";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String postModify(@Valid PostForm postForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return "createPost";
        }
        Post post = this.postService.getPost(id);
        if(!post.getUser().getLoginId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.modify(post, postForm.getTitle(), postForm.getContent());
        return "redirect:/user/board";
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(Model model,Principal principal, @PathVariable("id") Integer id,String loginId){
        Post post = this.postService.getPost(id);
        if(!post.getUser().getLoginId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(post);
        return "redirect:/user/board";
    }
}