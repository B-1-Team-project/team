package com.project.team.Restaurant;

import com.project.team.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public void registerRestaurant(String name, String address, String number, SiteUser owner) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setNumber(number);
        restaurant.setOwner(owner);
        this.restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurant(Integer id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        return restaurantOptional.orElse(null);
    }


}

