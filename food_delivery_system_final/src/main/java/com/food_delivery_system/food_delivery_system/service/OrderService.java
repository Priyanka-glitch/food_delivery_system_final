package com.food_delivery_system.food_delivery_system.service;

import com.food_delivery_system.food_delivery_system.model.Order;
import com.food_delivery_system.food_delivery_system.model.Restaurant;
import com.food_delivery_system.food_delivery_system.model.OrderItem;
import com.food_delivery_system.food_delivery_system.utils.ConcurrencyUtils;
import com.food_delivery_system.food_delivery_system.repo.OrderRepository;
import com.food_delivery_system.food_delivery_system.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Order placeOrder(Order order) {
            Long restaurantId = order.getRestaurant().getId();
            Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);

            if (restaurantOpt.isPresent()) {
                Restaurant restaurant = restaurantOpt.get();

                Lock lock = ConcurrencyUtils.getRestaurantLock(restaurantId);
                boolean lockAcquired = lock.tryLock();

                if (!lockAcquired) {
                    throw new RuntimeException("Restaurant is busy processing other orders. Please try again later.");
                }

                try {
                    if (restaurant.getCapacity() < order.getOrderItems().size()) {
                        throw new RuntimeException("Restaurant cannot fulfill the order due to capacity limits.");
                    }
                    return orderRepository.save(order);
                } finally {
                    lock.unlock();
                }
            } else {
                throw new RuntimeException("Restaurant not found with ID: " + restaurantId);
            }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }
}
