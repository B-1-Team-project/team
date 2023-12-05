package com.project.team.Restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.team.Reservation.Reservation;
import com.project.team.User.SiteUser;
import com.project.team.Review.Review;
import com.project.team.chat.Chat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 20)
    private String name;

    private String address;

    private String number;

    @ManyToOne
    private SiteUser owner;

    private LocalDateTime regDate;

    private String main;

    private List<String> facilities;

    private String locationX;

    private String locationY;

    private String image;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<Reservation> reservations;

    @Column(columnDefinition = "TEXT")
    private String introduce;

    private LocalTime startTime;

    private LocalTime endTime;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;

    private double averageStar;

    @ManyToMany
    private Set<SiteUser> favoriteUser;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<Chat> chatList;

}