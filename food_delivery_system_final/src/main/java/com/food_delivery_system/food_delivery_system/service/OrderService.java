package com.food_delivery_system.food_delivery_system.service;

import com.food_delivery_system.food_delivery_system.model.Order;
import com.food_delivery_system.food_delivery_system.model.Restaurant;
import com.food_delivery_system.food_delivery_system.model.OrderItem;
import com.food_delivery_system.food_delivery_system.model.MenuItem;
import com.food_delivery_system.food_delivery_system.repo.MenuItemRepository;
import com.food_delivery_system.food_delivery_system.strategy.LowestCosts;
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

    @Autowired
    private LowestCosts lowestCostStrategy;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public Order placeOrder(Order order) {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        if (allRestaurants.isEmpty()) {
            throw new RuntimeException("No restaurants available to fulfill the order.");
        }
        
        for (OrderItem orderItem : order.getOrderItems()) {
            String menuItemName = orderItem.getMenuItem().getName(); 

            MenuItem menuItem = menuItemRepository.findByName(menuItemName)
                    .orElseThrow(() -> new RuntimeException("MenuItem not found with name: " + menuItemName));

            orderItem.setMenuItem(menuItem); 
            orderItem.setOrder(order);        
        }
        
        Restaurant selectedRestaurant = lowestCostStrategy.selectRestaurant(order.getOrderItems(), allRestaurants);
        if (selectedRestaurant == null) {
            throw new RuntimeException("No restaurant can fulfill the order.");
        }

        Long restaurantId = selectedRestaurant.getId();
        Lock lock = ConcurrencyUtils.getRestaurantLock(restaurantId);
        boolean lockAcquired = lock.tryLock();

        if (!lockAcquired) {
            throw new RuntimeException("Restaurant is busy processing other orders. Please try again later.");
        }

        try {
            if (selectedRestaurant.getCapacity() < order.getOrderItems().size()) {
                throw new RuntimeException("Restaurant cannot fulfill the order due to capacity limits.");
            }
            
            order.setRestaurant(selectedRestaurant);
            
            return orderRepository.save(order);
        } finally {
            lock.unlock();  
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
