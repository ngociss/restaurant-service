package com.example.messaging.event;


import java.util.List;
import java.util.UUID;

public record OrderRejectedEvent(
        UUID orderId,
        UUID restaurantId,
        String status,
        List<String> failureMessages
) {}
