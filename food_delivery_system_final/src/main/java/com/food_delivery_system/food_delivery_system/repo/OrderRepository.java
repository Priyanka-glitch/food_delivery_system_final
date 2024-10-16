package com.food_delivery_system.food_delivery_system.repo;

import com.food_delivery_system.food_delivery_system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}