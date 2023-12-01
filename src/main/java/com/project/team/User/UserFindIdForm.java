package com.project.team.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFindIdForm {
    @NotEmpty(message = "이메일을 입력하세요.")
    @Email
    private String email;
}
