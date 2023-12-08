package com.project.team.Board.Post;

import com.project.team.Board.Answer.AnswerService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final AnswerService answerService;
    private final SiteUserService siteUserService;

    @GetMapping("/list")
    public String list(Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Post> paging = this.postService.getList(page);
        List<Post> postList = this.postService.getRecentList();
        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());
            model.addAttribute("user", user);
        }
        model.addAttribute("postList", postList);
        model.addAttribute("paging", paging);
        return "board";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(PostForm postForm) {
        return "createPost";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String create(@Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "createPost";
        }
        this.postService.createPost(postForm.getTitle(), postForm.getContent(), principal.getName());
        return "redirect:/post/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, Principal principal) {
        Post post = this.postService.getPost(id);
        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());
            model.addAttribute("user", user);
        }
        model.addAttribute("post", post);
        return "postDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(Model model, Principal principal, PostForm postForm, @PathVariable("id") Integer id) {
        Post post = this.postService.getPost(id);
        if (!post.getUser().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        model.addAttribute("post", post);
        return "createPost";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@Valid PostForm postForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "createPost";
        }
        Post post = this.postService.getPost(id);
        if (!post.getUser().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.modifyPost(post, postForm.getTitle(), postForm.getContent());
        return "redirect:/post/detail/" + post.getId();
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(Model model, Principal principal, @PathVariable("id") Integer id, String loginId) {
        Post post = this.postService.getPost(id);
        if (!post.getUser().getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.deletePost(post);
        return "redirect:/post/list";
    }
}