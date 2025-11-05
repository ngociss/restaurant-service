package com.example.application.dtos.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ValidateOrderCommand(
        UUID restaurantId,
        UUID orderId,
        BigDecimal totalPrice,
        List<OrderedItem> items
) {}

