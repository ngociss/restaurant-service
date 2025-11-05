package com.example.application.dtos.response;

import com.example.domain.valueobject.ApprovalStatus;

import java.util.List;
import java.util.UUID;

public record RestaurantApprovalResponse(
        UUID orderId,
        ApprovalStatus status,
        List<String> failureMessages
) {}