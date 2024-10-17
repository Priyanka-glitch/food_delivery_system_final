package com.food_delivery_system.food_delivery_system.service;

import com.food_delivery_system.food_delivery_system.model.MenuItem;
import com.food_delivery_system.food_delivery_system.model.Restaurant;
import com.food_delivery_system.food_delivery_system.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant registerRestaurant(Restaurant restaurant) {
        for (MenuItem menuItem : restaurant.getMenuItems()) {
            menuItem.setRestaurant(restaurant);
        }
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurantMenu(Long id, Restaurant restaurantDetails) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
        if (existingRestaurant.isPresent()) {
            Restaurant restaurant = existingRestaurant.get();
            restaurant.setName(restaurantDetails.getName());
            restaurant.setCapacity(restaurantDetails.getCapacity());
            restaurant.setRating(restaurantDetails.getRating());
            //restaurant.getMenuItems().clear();
            for (MenuItem newMenuItem : restaurantDetails.getMenuItems()) {
                newMenuItem.setRestaurant(restaurant);
                restaurant.getMenuItems().add(newMenuItem);
            }
            return restaurantRepository.save(restaurant);
        } else {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }
    }


    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }


    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }
}
