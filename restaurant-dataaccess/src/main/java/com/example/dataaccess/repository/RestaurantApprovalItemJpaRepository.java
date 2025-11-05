package com.example.dataaccess.repository;

import com.example.dataaccess.entity.RestaurantApprovalItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantApprovalItemJpaRepository extends JpaRepository<RestaurantApprovalItemEntity, UUID> {
}

