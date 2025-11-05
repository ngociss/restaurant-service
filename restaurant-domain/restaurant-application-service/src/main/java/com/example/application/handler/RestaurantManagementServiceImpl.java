package com.example.application.handler;

import com.example.application.dtos.command.CreateRestaurantCommand;
import com.example.application.ports.input.service.RestaurantManagementService;
import com.example.application.ports.output.repository.RestaurantRepository;
import com.example.domain.entity.Restaurant;
import com.example.domain.valueobject.RestaurantId;


import java.util.Optional;
import java.util.UUID;


public class RestaurantManagementServiceImpl implements RestaurantManagementService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantManagementServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantCommand command) {

        Restaurant restaurant = new Restaurant(
                new RestaurantId(UUID.randomUUID()),
                command.name(),
                command.isActive()
        );
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Optional<Restaurant> getRestaurant(UUID restaurantId) {
        return restaurantRepository.findById(new RestaurantId(restaurantId));
    }
}

