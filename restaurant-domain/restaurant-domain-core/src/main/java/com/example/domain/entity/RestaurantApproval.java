package com.example.domain.entity;


import com.example.domain.valueobject.ApprovalStatus;
import com.example.domain.valueobject.OrderId;
import com.example.domain.valueobject.RestaurantId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Rich Domain Model - Entity chứa business logic
 * Không cần Domain Service, logic nằm trong chính entity
 */
public class RestaurantApproval extends AggregateRoot<UUID> {

    private final RestaurantId restaurantId;
    private final OrderId orderId;
    private ApprovalStatus status;
    private List<String> failureMessages;

    public RestaurantApproval(UUID id, RestaurantId restaurantId, OrderId orderId) {
        setId(id);
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.status = ApprovalStatus.PENDING;
        this.failureMessages = new ArrayList<>();
    }

    /**
     * Factory method để tạo và validate approval
     * Business logic nằm trong entity (Rich Domain Model)
     */
    public static RestaurantApproval validateAndCreate(
            Restaurant restaurant,
            OrderId orderId,
            BigDecimal totalPrice,
            boolean allItemsAvailable) {

        RestaurantApproval approval = new RestaurantApproval(
                UUID.randomUUID(),
                restaurant.getId(),
                orderId
        );

        List<String> reasons = new ArrayList<>();

        // Validate business rules
        if (!restaurant.isActive()) {
            reasons.add("RESTAURANT_INACTIVE");
        }
        if (!allItemsAvailable) {
            reasons.add("ITEM_OUT_OF_STOCK");
        }
        if (totalPrice.compareTo(BigDecimal.valueOf(5000000)) > 0) {
            reasons.add("PRICE_LIMIT_EXCEEDED");
        }

        // Approve hoặc reject dựa trên validation
        if (reasons.isEmpty()) {
            approval.approve();
        } else {
            approval.reject(reasons);
        }

        return approval;
    }

    public void approve() {
        this.status = ApprovalStatus.APPROVED;
    }

    public void reject(List<String> reasons) {
        this.status = ApprovalStatus.REJECTED;
        this.failureMessages = reasons;
    }

    public RestaurantId getRestaurantId() { return restaurantId; }
    public OrderId getOrderId() { return orderId; }
    public ApprovalStatus getStatus() { return status; }
    public List<String> getFailureMessages() { return failureMessages; }
}
