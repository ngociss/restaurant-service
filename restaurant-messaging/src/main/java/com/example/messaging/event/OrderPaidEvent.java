package com.example.messaging.event;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderPaidEvent(
        UUID orderId,
        UUID restaurantId,
        BigDecimal totalAmount,
        List<Item> items
) {
    public record Item(
            UUID productId,
            int quantity
    ) {}
}
