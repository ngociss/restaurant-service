package com.example.dataaccess.adapter;

import com.example.application.ports.output.repository.RestaurantApprovalRepository;
import com.example.dataaccess.entity.RestaurantApprovalEntity;
import com.example.dataaccess.mapper.RestaurantDataAccessMapper;
import com.example.dataaccess.repository.RestaurantApprovalJpaRepository;
import com.example.domain.entity.RestaurantApproval;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RestaurantApprovalRepositoryImpl implements RestaurantApprovalRepository {

    private final RestaurantApprovalJpaRepository jpa;
    private final RestaurantDataAccessMapper mapper = new RestaurantDataAccessMapper();

    public RestaurantApprovalRepositoryImpl(RestaurantApprovalJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    @Transactional
    public void save(RestaurantApproval approval) {
        RestaurantApprovalEntity e = mapper.toEntity(approval);
        jpa.save(e);
    }
}

