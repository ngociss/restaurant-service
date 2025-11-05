package com.example.application.handler;

import com.example.application.dtos.command.CreateProductCommand;
import com.example.application.dtos.response.ProductInfo;
import com.example.application.ports.input.service.ProductManagementService;
import com.example.application.ports.output.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class ProductManagementServiceImpl implements ProductManagementService {

    private final ProductRepository productRepository;

    public ProductManagementServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductInfo increaseStock(UUID productId, int quantity) {
        ProductInfo product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        int newStock = product.stock() + quantity;
        return productRepository.updateStock(productId, newStock);
    }
    @Override
    public ProductInfo decreaseStock(UUID productId, int quantity) {
        ProductInfo product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        if (product.stock() < quantity) {
            throw new RuntimeException(
                    String.format("Insufficient stock. Product: %s, Current: %d, Requested: %d",
                            productId, product.stock(), quantity));
        }
        int newStock = product.stock() - quantity;
        return productRepository.updateStock(productId, newStock);
    }

    @Override
    public ProductInfo setStock(UUID productId, int value) {
        ProductInfo product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
        return productRepository.updateStock(productId, value);
    }

    @Override
    public List<ProductInfo> getProductsByRestaurant(UUID restaurantId) {
        return productRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Optional<ProductInfo> getProduct(UUID productId) {
        return productRepository.findById(productId);
    }

    @Override
    public ProductInfo createProduct(CreateProductCommand command) {
        ProductInfo newProduct = new ProductInfo(
                UUID.randomUUID(),
                command.name(),
                command.price(),
                command.stock() != null ? command.stock() : 100,
                command.available() != null ? command.available() : true
        );

        return productRepository.createProduct(command.restaurantId(), newProduct);
    }
}
