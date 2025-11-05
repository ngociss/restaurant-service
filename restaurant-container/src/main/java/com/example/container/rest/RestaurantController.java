package com.example.container.rest;


import com.example.application.dtos.command.CreateProductCommand;
import com.example.application.dtos.command.CreateRestaurantCommand;
import com.example.application.dtos.command.ValidateOrderCommand;
import com.example.application.dtos.response.ProductInfo;
import com.example.application.dtos.response.RestaurantApprovalResponse;
import com.example.application.ports.input.service.ProductManagementService;
import com.example.application.ports.input.service.RestaurantApprovalService;
import com.example.application.ports.input.service.RestaurantManagementService;
import com.example.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/restaurants")
public class RestaurantController {

    private final RestaurantApprovalService restaurantApprovalService;
    private final ProductManagementService productManagementService;
    private final RestaurantManagementService restaurantManagementService;


    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantCommand command) {
        log.info("Creating new restaurant: {}", command.name());
        Restaurant restaurant = restaurantManagementService.createRestaurant(command);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductInfo> createProduct(@RequestBody CreateProductCommand command) {
        log.info("Creating new product: {} for restaurantId: {}", command.name(), command.restaurantId());
        ProductInfo product = productManagementService.createProduct(command);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable(name = "restaurantId") String restaurantId) {
        return restaurantManagementService.getRestaurant(UUID.fromString(restaurantId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{restaurantId}/products")
    public ResponseEntity<List<ProductInfo>> getProducts(@PathVariable(name = "restaurantId") String restaurantId) {
        List<ProductInfo> products = productManagementService.getProductsByRestaurant(UUID.fromString(restaurantId));
        return ResponseEntity.ok(products);
    }


    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductInfo> getProduct(@PathVariable(name = "productId") String productId) {
        return productManagementService.getProduct(UUID.fromString(productId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/products/{productId}/increase-stock")
    public ResponseEntity<ProductInfo> increaseStock(
            @PathVariable(name = "productId") String productId,
            @RequestParam(name = "quantity") int quantity) {

        ProductInfo result = productManagementService.increaseStock(UUID.fromString(productId), quantity);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/products/{productId}/decrease-stock")
    public ResponseEntity<ProductInfo> decreaseStock(
            @PathVariable(name = "productId") String productId,
            @RequestParam(name = "quantity") int quantity) {

        ProductInfo result = productManagementService.decreaseStock(UUID.fromString(productId), quantity);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/products/{productId}/stock")
    public ResponseEntity<ProductInfo> setStock(
            @PathVariable(name = "productId") UUID productId,
            @RequestParam(name = "value") int value) {

        ProductInfo result = productManagementService.setStock(productId, value);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validate-order")
    public ResponseEntity<RestaurantApprovalResponse> validateOrder(@RequestBody ValidateOrderCommand command) {
        log.info("Testing order validation for restaurantId: {}, orderId: {}",
                 command.restaurantId(), command.orderId());

        RestaurantApprovalResponse response = restaurantApprovalService.validateOrder(command);

        log.info("Validation result - Status: {}, Messages: {}",
                 response.status(), response.failureMessages());

        return ResponseEntity.ok(response);
    }

}
