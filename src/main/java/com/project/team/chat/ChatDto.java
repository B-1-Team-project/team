package com.project.team.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private String content;
    private String writer;
    private String target;
    private String room;
    private Integer restaurant;
    private String type;
}
