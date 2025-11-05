package com.example.application.ports.output.repository;

import com.example.application.dtos.response.ProductInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {


    Optional<ProductInfo> findById(UUID productId);

    ProductInfo updateStock(UUID productId, int newStock);

    List<ProductInfo> findByRestaurantId(UUID restaurantId);
    ProductInfo createProduct(UUID restaurantId, ProductInfo product);


}
