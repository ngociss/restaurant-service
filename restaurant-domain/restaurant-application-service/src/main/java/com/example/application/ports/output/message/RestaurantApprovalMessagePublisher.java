package com.example.application.ports.output.message;

import java.util.List;
import java.util.UUID;

/**
 * Output port for publishing restaurant approval decisions
 * Clean Architecture: This is a port (interface) in the application layer
 */
public interface RestaurantApprovalMessagePublisher {

    void publishOrderApproved(UUID orderId, UUID restaurantId);

    void publishOrderRejected(UUID orderId, UUID restaurantId, List<String> failureMessages);
}

