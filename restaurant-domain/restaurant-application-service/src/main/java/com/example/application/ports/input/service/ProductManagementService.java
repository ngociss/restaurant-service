package com.example.application.ports.input.service;

import com.example.application.dtos.command.CreateProductCommand;
import com.example.application.dtos.response.ProductInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductManagementService {

    ProductInfo increaseStock(UUID productId, int quantity);
    ProductInfo decreaseStock(UUID productId, int quantity);
    ProductInfo setStock(UUID productId, int value);

    List<ProductInfo> getProductsByRestaurant(UUID restaurantId);
    Optional<ProductInfo> getProduct(UUID productId);
    ProductInfo createProduct(CreateProductCommand command);
}
