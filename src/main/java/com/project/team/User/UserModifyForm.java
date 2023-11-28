package com.project.team.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyForm {

    @Size(min = 3, max = 25)
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotEmpty(message = "패스워드를 입력해주세요.")
    private String password1;

    @NotEmpty(message = "비밀번호를 재입력해주세요.")
    private String password2;

    @NotBlank(message = "권한을 선택해주세요.")
    private String authority;
}
