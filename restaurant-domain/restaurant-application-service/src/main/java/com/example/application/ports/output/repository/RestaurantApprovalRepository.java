package com.example.application.ports.output.repository;

import com.example.domain.entity.RestaurantApproval;

public interface RestaurantApprovalRepository {
    void save(RestaurantApproval approval);
}
