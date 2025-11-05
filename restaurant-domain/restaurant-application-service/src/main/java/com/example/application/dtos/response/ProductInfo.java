package com.example.application.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInfo(
        UUID id,
        String name,
        BigDecimal price,
        Integer stock,
        Boolean available
) {}

