package com.example.dataaccess.adapter;

import com.example.application.dtos.command.OrderedItem;
import com.example.application.ports.output.repository.RestaurantRepository;
import com.example.dataaccess.entity.RestaurantProductEntity;
import com.example.dataaccess.mapper.RestaurantDataAccessMapper;
import com.example.dataaccess.repository.RestaurantJpaRepository;
import com.example.dataaccess.repository.RestaurantProductJpaRepository;
import com.example.domain.entity.Restaurant;
import com.example.domain.valueobject.RestaurantId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpa;
    private final RestaurantProductJpaRepository productJpa;
    private final RestaurantDataAccessMapper mapper;

    @Override
    public Optional<Restaurant> findById(RestaurantId restaurantId) {
        return restaurantJpa.findById(restaurantId.value())
                .map(mapper::toDomain);
    }

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant) {
        var entity = mapper.toEntity(restaurant);
        var saved = restaurantJpa.save(entity);
        return mapper.toDomain(saved);
    }


    @Override
    public Map<UUID, String> validateStockAvailability(RestaurantId restaurantId, List<OrderedItem> items) {
        Map<UUID, String> errors = new HashMap<>();

        if (items == null || items.isEmpty()) {
            return errors;
        }
        List<UUID> productIds = items.stream()
                .map(OrderedItem::productId)
                .toList();

        List<RestaurantProductEntity> products = productJpa.findAvailableProducts(
                restaurantId.value(),
                productIds
        );
        Map<UUID, RestaurantProductEntity> productMap = products.stream()
                .collect(Collectors.toMap(
                        RestaurantProductEntity::getId, // Sử dụng getId() thay vì getProductId()
                        p -> p
                ));
        for (OrderedItem item : items) {
            RestaurantProductEntity product = productMap.get(item.productId());

            if (product == null) {
                errors.put(item.productId(),
                    "Product not found or not available in restaurant");
            } else if (product.getStock() < item.quantity()) {
                errors.put(item.productId(),
                    String.format("Insufficient stock. Requested: %d, Available: %d",
                        item.quantity(), product.getStock()));
            }
        }

        return errors;
    }

    @Transactional
    @Override
    public boolean decreaseStockForItems(RestaurantId restaurantId, List<OrderedItem> items) {
        if (items == null || items.isEmpty()) {
            return true;
        }

        for (OrderedItem item : items) {
            int updated = productJpa.decreaseStock(item.productId(), item.quantity());
            if (updated == 0) {
                log.error("Failed to decrease stock for product: {}, quantity: {}",
                    item.productId(), item.quantity());
                return false;
            }
        }

        log.info("Successfully decreased stock for {} items in restaurant {}",
            items.size(), restaurantId.value());
        return true;
    }

}
