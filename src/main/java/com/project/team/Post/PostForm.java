package com.project.team.Post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
    @NotEmpty(message = "제목을 입력해주세요")
    @Size(max = 20)
    private String title;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    private String user;
}
