package com.project.team.Restaurant.sort;

import com.project.team.Restaurant.Restaurant;

import java.util.Comparator;

public class SortByAverageStar implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant r1, Restaurant r2) {
        return Double.compare(r2.getAverageStar(), r1.getAverageStar());
    }
}
