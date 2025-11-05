package com.example.messaging.event;


import java.util.UUID;

public record OrderApprovedEvent(
        UUID orderId,
        UUID restaurantId,
        String status
) {}
