package com.project.team.socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDto {
    private String content;
    private String sender;
    private String recipient;
}
