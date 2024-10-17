package com.food_delivery_system.food_delivery_system.strategy;

import com.food_delivery_system.food_delivery_system.model.OrderItem;
import com.food_delivery_system.food_delivery_system.model.MenuItem;
import com.food_delivery_system.food_delivery_system.model.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LowestCosts implements Selection {

    @Override
    public Restaurant selectRestaurant(List<OrderItem> orderItems, List<Restaurant> restaurants) {
        Restaurant selectedRestaurant = null;
        double lowestCost = Double.MAX_VALUE;

        for (Restaurant restaurant : restaurants) {
            double totalCost = calculateTotalCost(orderItems, restaurant.getMenuItems());
            if (totalCost < lowestCost) {
                lowestCost = totalCost;
                selectedRestaurant = restaurant;
            }
        }

        return selectedRestaurant;
    }


    public double calculateTotalCost(List<OrderItem> orderItems, List<MenuItem> restaurantMenuItems) {
        double totalCost = 0.0;

        for (OrderItem orderItem : orderItems) {
            MenuItem matchingMenuItem = restaurantMenuItems.stream()
                    .filter(menuItem -> menuItem.getId().equals(orderItem.getMenuItem().getId()))
                    .findFirst()
                    .orElse(null);

            if (matchingMenuItem == null) {
                return Double.MAX_VALUE;  // Skip this restaurant if any item is not available
            }

            totalCost += matchingMenuItem.getPrice() * orderItem.getQuantity();
        }

        return totalCost;
    }
}
