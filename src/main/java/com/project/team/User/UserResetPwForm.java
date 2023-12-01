package com.project.team.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResetPwForm {
    private String token;

    @NotEmpty(message = "변경할 비밀번호를 입력하세요.")
    private String password1;

    @NotEmpty(message = "변경할 비밀번호를 확인해주세요.")
    private String password2;
}
