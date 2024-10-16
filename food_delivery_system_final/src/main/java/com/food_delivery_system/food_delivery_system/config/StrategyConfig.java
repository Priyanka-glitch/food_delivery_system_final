package com.food_delivery_system.food_delivery_system.config;

import com.food_delivery_system.food_delivery_system.strategy.LowestCosts;
import org.springframework.context.annotation.Bean;

public class StrategyConfig {
    @Bean
    public LowestCosts selectionStrategy() {
        return new LowestCosts();
    }
}
