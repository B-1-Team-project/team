package com.project.team.review;

import com.project.team.Restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByRestaurant(Restaurant restaurant);
}
