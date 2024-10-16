package com.food_delivery_system.food_delivery_system.strategy;

import com.food_delivery_system.food_delivery_system.model.OrderItem;
import com.food_delivery_system.food_delivery_system.model.Restaurant;

import java.util.List;

public interface Selection {
    /**
     * Selects a restaurant based on the provided order items and available restaurants.
     *
     * @param orderItems The list of items being ordered.
     * @param restaurants The list of available restaurants.
     * @return The selected restaurant that fulfills the order.
     */
    Restaurant selectRestaurant(List<OrderItem> orderItems, List<Restaurant> restaurants);
}
