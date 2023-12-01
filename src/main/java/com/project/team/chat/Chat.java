package com.project.team.chat;

import com.project.team.Restaurant.Restaurant;
import com.project.team.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    private SiteUser writer;
    @ManyToOne
    private SiteUser target;
    private String room;
    @ManyToOne
    private Restaurant restaurant;
    private LocalDateTime createDate;
}
