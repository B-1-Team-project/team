package com.project.team.alarm;

import com.project.team.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private SiteUser target;
    @ManyToOne
    private SiteUser user;
    private String type;
}
