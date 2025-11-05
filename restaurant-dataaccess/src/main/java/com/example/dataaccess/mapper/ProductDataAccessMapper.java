package com.example.dataaccess.mapper;

import com.example.application.dtos.response.ProductInfo;
import com.example.dataaccess.entity.RestaurantProductEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper để convert giữa Entity (Infrastructure) và DTO (Application)
 * DataAccess Layer: Chịu trách nhiệm mapping
 */
@Component
public class ProductDataAccessMapper {

    /**
     * Convert Entity sang DTO
     */
    public ProductInfo toDTO(RestaurantProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ProductInfo(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock(),
                entity.isAvailable()
        );
    }
}

