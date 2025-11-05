package com.example.application.ports.input.message;

import com.example.application.dtos.command.ValidateOrderCommand;

/**
 * Input port for handling restaurant approval requests from messaging system
 * Clean Architecture: This is a port (interface) in the application layer
 */
public interface RestaurantApprovalRequestMessageListener {

    void validateOrderForApproval(ValidateOrderCommand command);
}

