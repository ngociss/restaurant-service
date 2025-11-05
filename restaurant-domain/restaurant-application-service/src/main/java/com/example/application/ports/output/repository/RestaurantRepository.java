package com.example.application.ports.output.repository;


import com.example.application.dtos.command.OrderedItem;
import com.example.domain.entity.Restaurant;
import com.example.domain.valueobject.RestaurantId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {
    Optional<Restaurant> findById(RestaurantId restaurantId);

    Restaurant save(Restaurant restaurant);
    Map<UUID, String> validateStockAvailability(RestaurantId restaurantId, List<OrderedItem> items);
    boolean decreaseStockForItems(RestaurantId restaurantId, List<OrderedItem> items);
}
