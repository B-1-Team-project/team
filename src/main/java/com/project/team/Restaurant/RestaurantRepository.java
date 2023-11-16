package com.project.team.Restaurant;

import com.project.team.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

}
