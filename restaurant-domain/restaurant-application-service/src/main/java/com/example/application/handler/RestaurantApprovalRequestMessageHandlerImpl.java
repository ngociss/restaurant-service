package com.example.application.handler;

import com.example.application.dtos.command.ValidateOrderCommand;
import com.example.application.dtos.response.RestaurantApprovalResponse;
import com.example.application.ports.input.message.RestaurantApprovalRequestMessageListener;
import com.example.application.ports.input.service.RestaurantApprovalService;
import com.example.application.ports.output.message.RestaurantApprovalMessagePublisher;
import com.example.domain.valueobject.ApprovalStatus;


public class RestaurantApprovalRequestMessageHandlerImpl implements RestaurantApprovalRequestMessageListener {

    private final RestaurantApprovalService restaurantApprovalService;
    private final RestaurantApprovalMessagePublisher messagePublisher;

    public RestaurantApprovalRequestMessageHandlerImpl(RestaurantApprovalService restaurantApprovalService,
                                                       RestaurantApprovalMessagePublisher messagePublisher) {
        this.restaurantApprovalService = restaurantApprovalService;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void validateOrderForApproval(ValidateOrderCommand command) {

        try {
            RestaurantApprovalResponse response = restaurantApprovalService.validateOrder(command);
            if (response.status() == ApprovalStatus.APPROVED) {
                messagePublisher.publishOrderApproved(command.orderId(), command.restaurantId());
            } else {
                messagePublisher.publishOrderRejected(
                        command.orderId(),
                        command.restaurantId(),
                        response.failureMessages()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process restaurant approval request", e);
        }
    }
}

