package com.project.team.Recommend;

import com.project.team.Restaurant.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendResponse {

    private List<Recommend> recommendList;

    private List<Restaurant> restaurantList;

    public RecommendResponse(List<Recommend> recommendList, List<Restaurant> restaurantList) {
        this.recommendList = recommendList;
        this.restaurantList = restaurantList;
    }

}
