package com.project.team.Restaurant.sort;

import com.project.team.Restaurant.Restaurant;

import java.util.Comparator;

public class SortByReview implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        return Integer.compare(o1.getReviews().size(), o2.getReviews().size());
    }
}
