package com.example.messaging.publisher;

import com.example.application.ports.output.message.RestaurantApprovalMessagePublisher;
import com.example.messaging.event.OrderApprovedEvent;
import com.example.messaging.event.OrderRejectedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Kafka adapter that implements the output port for publishing approval decisions
 * Clean Architecture: This is an adapter in the infrastructure layer
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderDecisionPublisher implements RestaurantApprovalMessagePublisher {

    private static final String ORDER_APPROVED_TOPIC = "order-approved";
    private static final String ORDER_REJECTED_TOPIC = "order-rejected";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishOrderApproved(UUID orderId, UUID restaurantId) {
        log.info("Publishing order approved event for order: {}, restaurant: {}", orderId, restaurantId);
        OrderApprovedEvent event = new OrderApprovedEvent(orderId, restaurantId, "APPROVED");
        kafkaTemplate.send(ORDER_APPROVED_TOPIC, orderId.toString(), event);
    }

    @Override
    public void publishOrderRejected(UUID orderId, UUID restaurantId, List<String> failureMessages) {
        log.info("Publishing order rejected event for order: {}, restaurant: {}", orderId, restaurantId);
        OrderRejectedEvent event = new OrderRejectedEvent(orderId, restaurantId, "REJECTED", failureMessages);
        kafkaTemplate.send(ORDER_REJECTED_TOPIC, orderId.toString(), event);
    }
}
