package com.example.application.ports.input.service;

import com.example.application.dtos.command.CreateRestaurantCommand;
import com.example.domain.entity.Restaurant;

import java.util.Optional;
import java.util.UUID;


public interface RestaurantManagementService {

    Restaurant createRestaurant(CreateRestaurantCommand command);
    Optional<Restaurant> getRestaurant(UUID restaurantId);
}

