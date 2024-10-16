package com.food_delivery_system.food_delivery_system.controllers;

import com.food_delivery_system.food_delivery_system.model.Restaurant;
import com.food_delivery_system.food_delivery_system.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> registerRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.registerRestaurant(restaurant);
        return ResponseEntity.ok(savedRestaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurantMenu(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurantMenu(id, restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }
}