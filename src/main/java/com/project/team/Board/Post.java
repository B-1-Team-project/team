package com.project.team.Board;

import com.project.team.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    @ManyToOne
    private SiteUser user;
    @OneToMany(mappedBy = "post" , cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

}