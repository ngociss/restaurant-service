package com.example.application.ports.input.service;


import com.example.application.dtos.command.ValidateOrderCommand;
import com.example.application.dtos.response.RestaurantApprovalResponse;

public interface RestaurantApprovalService {
    RestaurantApprovalResponse validateOrder(ValidateOrderCommand command);
}
