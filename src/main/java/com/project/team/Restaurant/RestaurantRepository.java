package com.project.team.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByAddress(String address);

    @Query("SELECT r FROM Restaurant r WHERE r.main LIKE :main1 OR r.main LIKE :main2 OR r.main LIKE :main3 ORDER BY r.averageStar DESC LIMIT 3")
    List<Restaurant> byMain(@Param("main1") String main1, @Param("main2") String main2, @Param("main3") String main3);

    @Query("SELECT r FROM Restaurant r WHERE CAST(r.locationX AS DOUBLE) BETWEEN :x - :radius AND :x + :radius AND CAST(r.locationY AS DOUBLE) BETWEEN :y - :radius AND :y + :radius")
    List<Restaurant> byAround(@Param("x") Double x, @Param("y") Double y, @Param("radius") Double radius);
}
