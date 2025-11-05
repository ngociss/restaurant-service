package com.example.dataaccess.adapter;

import com.example.application.dtos.response.ProductInfo;
import com.example.application.ports.output.repository.ProductRepository;
import com.example.dataaccess.entity.RestaurantProductEntity;
import com.example.dataaccess.mapper.ProductDataAccessMapper;
import com.example.dataaccess.repository.RestaurantProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final RestaurantProductJpaRepository jpaRepository;
    private final ProductDataAccessMapper mapper;



    @Override
    public Optional<ProductInfo> findById(UUID productId) {
        return jpaRepository.findById(productId)
                .map(mapper::toDTO);
    }


    @Transactional
    @Override
    public ProductInfo updateStock(UUID productId, int newStock) {
        RestaurantProductEntity entity = jpaRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        entity.setStock(newStock);
        RestaurantProductEntity saved = jpaRepository.save(entity);

        return mapper.toDTO(saved);
    }

    @Override
    public List<ProductInfo> findByRestaurantId(UUID restaurantId) {
        return jpaRepository.findAll()
                .stream()
                .filter(p -> p.getRestaurantId().equals(restaurantId))
                .map(mapper::toDTO)
                .toList();
    }


    @Transactional
    @Override
    public ProductInfo createProduct(UUID restaurantId, ProductInfo product) {
        // Tạo entity mới từ ProductInfo
        RestaurantProductEntity entity = new RestaurantProductEntity();
        entity.setId(product.id());
        entity.setRestaurantId(restaurantId);
        entity.setName(product.name());
        entity.setPrice(product.price());
        entity.setStock(product.stock());
        entity.setAvailable(true); // Mặc định available = true

        // Save vào database
        RestaurantProductEntity saved = jpaRepository.save(entity);

        return mapper.toDTO(saved);
    }
}
