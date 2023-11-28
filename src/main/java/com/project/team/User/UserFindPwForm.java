package com.project.team.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFindPwForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "로그인 아이디를 입력하세요.")
    private String loginId;
}
