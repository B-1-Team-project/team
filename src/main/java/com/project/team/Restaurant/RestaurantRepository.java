package com.project.team.Restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByAddress(String address);

    List<Restaurant> findTop3ByOrderByAverageStarDesc();

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.reviews ORDER BY SIZE(r.reviews) DESC LIMIT 3")
    List<Restaurant> findTop3ByOrderByReviewsCountDesc();


    @Query("SELECT r FROM Restaurant r WHERE r.main LIKE :main1 OR r.main LIKE :main2 OR r.main LIKE :main3 ORDER BY r.averageStar DESC LIMIT 3")
    List<Restaurant> byMain(@Param("main1") String main1, @Param("main2") String main2, @Param("main3") String main3);

}
