package com.project.team.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSelectForm {
    @NotBlank(message = "권한을 선택해주세요.")
    private String authority;
}
