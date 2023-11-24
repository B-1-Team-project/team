package com.project.team.Comment;

import com.project.team.Review.Review;
import com.project.team.Review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private final ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Integer id, String comment) {
        Review review = this.reviewService.getReview(id);
        this.commentService.createComment(review, comment);
        return String.format("redirect:/restaurant/detail/%s", review.getRestaurant().getId());
    }
}
