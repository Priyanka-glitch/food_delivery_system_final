package com.food_delivery_system.food_delivery_system.strategy;

import com.food_delivery_system.food_delivery_system.model.OrderItem;
import com.food_delivery_system.food_delivery_system.model.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HighestRated implements Selection {

    @Override
    public Restaurant selectRestaurant(List<OrderItem> orderItems, List<Restaurant> restaurants) {
        Restaurant selectedRestaurant = null;

        for (Restaurant restaurant : restaurants) {
            if (selectedRestaurant == null || restaurant.getRating() > selectedRestaurant.getRating()) {
                selectedRestaurant = restaurant;
            }
        }

        return selectedRestaurant;
    }
}