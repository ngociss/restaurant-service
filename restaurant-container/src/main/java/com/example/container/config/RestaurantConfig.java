package com.example.container.config;


import com.example.application.handler.ProductManagementServiceImpl;
import com.example.application.handler.RestaurantApprovalRequestMessageHandlerImpl;
import com.example.application.handler.RestaurantApprovalServiceImpl;
import com.example.application.handler.RestaurantManagementServiceImpl;
import com.example.application.ports.input.message.RestaurantApprovalRequestMessageListener;
import com.example.application.ports.input.service.ProductManagementService;
import com.example.application.ports.input.service.RestaurantApprovalService;
import com.example.application.ports.input.service.RestaurantManagementService;
import com.example.application.ports.output.message.RestaurantApprovalMessagePublisher;
import com.example.application.ports.output.repository.ProductRepository;
import com.example.application.ports.output.repository.RestaurantApprovalRepository;
import com.example.application.ports.output.repository.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantConfig {

    @Bean
    public RestaurantApprovalService restaurantApprovalService(
            RestaurantRepository restaurantRepo,
            RestaurantApprovalRepository approvalRepo
    ) {
        return new RestaurantApprovalServiceImpl(restaurantRepo, approvalRepo);
    }

    @Bean
    public ProductManagementService productManagementService(
            ProductRepository productRepository
    ) {
        return new ProductManagementServiceImpl(productRepository);
    }

    @Bean
    public RestaurantManagementService restaurantManagementService(
            RestaurantRepository restaurantRepository
    )
    {
        return new RestaurantManagementServiceImpl(restaurantRepository);
    }

    @Bean
    public RestaurantApprovalRequestMessageListener restaurantApprovalRequestMessageListener(
            RestaurantApprovalService restaurantApprovalService,
            RestaurantApprovalMessagePublisher messagePublisher
    ) {
        return new RestaurantApprovalRequestMessageHandlerImpl(restaurantApprovalService, messagePublisher);
    }


}
