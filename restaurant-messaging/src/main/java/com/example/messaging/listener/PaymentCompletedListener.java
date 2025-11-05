package com.example.messaging.listener;

import com.example.application.ports.input.message.RestaurantApprovalRequestMessageListener;
import com.example.messaging.event.OrderPaidEvent;
import com.example.messaging.mapper.RestaurantMessagingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka listener adapter that receives payment completed events
 * Clean Architecture: This is an adapter in the infrastructure layer
 * It translates Kafka events to application commands and delegates to the application layer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedListener {


    private static final String GROUP_ID = "restaurant-group";

    private final RestaurantApprovalRequestMessageListener messageHandler;
    private final RestaurantMessagingMapper messagingMapper;

    @KafkaListener(topics = "order-paid-topic", groupId = GROUP_ID)
    public void onOrderPaidEvent(OrderPaidEvent event) {
        log.info("📩 Received PaymentCompletedEvent for order: {}, restaurant: {}",
                event.orderId(), event.restaurantId());

        try {
            var command = messagingMapper.orderPaidEventToValidateOrderCommand(event);
            messageHandler.validateOrderForApproval(command);
        } catch (Exception e) {
            log.error("Error processing payment completed event for order: {}", event.orderId(), e);
            // In production, consider: dead letter queue, retry logic, or compensation
            throw e;
        }
    }
}
