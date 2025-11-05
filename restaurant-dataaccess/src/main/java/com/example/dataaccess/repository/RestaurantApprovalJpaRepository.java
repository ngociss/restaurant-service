package com.example.dataaccess.repository;
import com.example.dataaccess.entity.RestaurantApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantApprovalJpaRepository extends JpaRepository<RestaurantApprovalEntity, UUID> {}
