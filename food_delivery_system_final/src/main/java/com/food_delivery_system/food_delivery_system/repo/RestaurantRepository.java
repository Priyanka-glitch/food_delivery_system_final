package com.food_delivery_system.food_delivery_system.repo;

import com.food_delivery_system.food_delivery_system.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAll();

}
