package com.example.application.dtos.command;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductCommand(
        UUID restaurantId,
        String name,
        BigDecimal price,
        Integer stock,
        Boolean available
) {}

