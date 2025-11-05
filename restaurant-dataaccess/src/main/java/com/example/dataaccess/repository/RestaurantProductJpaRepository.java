package com.example.dataaccess.repository;
import com.example.dataaccess.entity.RestaurantProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RestaurantProductJpaRepository extends JpaRepository<RestaurantProductEntity, UUID> {


    @Query("""
        SELECT COUNT(p) 
        FROM RestaurantProductEntity p
        WHERE p.restaurantId = :restaurantId
          AND p.id IN :productIds
          AND p.available = true
    """)
    long countAvailable(@Param("restaurantId") UUID restaurantId,
                       @Param("productIds") List<UUID> productIds);

    @Query("""
        SELECT p
        FROM RestaurantProductEntity p
        WHERE p.restaurantId = :restaurantId
          AND p.id IN :productIds
          AND p.available = true
    """)
    List<RestaurantProductEntity> findAvailableProducts(
            @Param("restaurantId") UUID restaurantId,
            @Param("productIds") List<UUID> productIds);

    @Modifying
    @Query("""
        UPDATE RestaurantProductEntity p
        SET p.stock = p.stock - :quantity
        WHERE p.id = :productId
          AND p.stock >= :quantity
    """)
    int decreaseStock(@Param("productId") UUID productId,
                     @Param("quantity") int quantity);

}
