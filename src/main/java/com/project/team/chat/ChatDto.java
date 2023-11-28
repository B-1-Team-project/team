package com.project.team.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDto {
    private String content;
    private String writer;
    private String target;
    private String room;
}
