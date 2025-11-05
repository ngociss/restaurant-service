package com.example.dataaccess.mapper;

import com.example.dataaccess.entity.RestaurantApprovalEntity;
import com.example.dataaccess.entity.RestaurantEntity;
import com.example.domain.entity.Restaurant;
import com.example.domain.entity.RestaurantApproval;
import com.example.domain.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RestaurantDataAccessMapper {

    public Restaurant toDomain(RestaurantEntity e) {
        if (e == null) return null;
        return new Restaurant(new RestaurantId(e.getId()), e.getName(), e.isActive());
    }

    public RestaurantEntity toEntity(Restaurant restaurant) {
        if (restaurant == null) return null;
        RestaurantEntity e = new RestaurantEntity();
        e.setId(restaurant.getId().value());
        e.setName(restaurant.getName());
        e.setActive(restaurant.isActive());
        return e;
    }

    public RestaurantApprovalEntity toEntity(RestaurantApproval approval) {
        RestaurantApprovalEntity e = new RestaurantApprovalEntity();
        e.setId(approval.getId());
        e.setRestaurantId(approval.getRestaurantId().value());
        e.setOrderId(approval.getOrderId().value());
        e.setStatus(approval.getStatus());

        // Convert List<String> sang String[] để lưu vào PostgreSQL TEXT[]
        e.setFailureMessages(
                approval.getFailureMessages() == null || approval.getFailureMessages().isEmpty()
                        ? null
                        : approval.getFailureMessages().toArray(new String[0])
        );

        e.setCreatedAt(Instant.now());
        return e;
    }
}
