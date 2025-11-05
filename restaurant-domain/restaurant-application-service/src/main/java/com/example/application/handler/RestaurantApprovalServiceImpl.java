package com.example.application.handler;

import com.example.application.dtos.command.ValidateOrderCommand;
import com.example.application.dtos.response.RestaurantApprovalResponse;
import com.example.application.ports.input.service.RestaurantApprovalService;
import com.example.application.ports.output.repository.RestaurantApprovalRepository;
import com.example.application.ports.output.repository.RestaurantRepository;
import com.example.domain.entity.Restaurant;
import com.example.domain.entity.RestaurantApproval;
import com.example.domain.valueobject.OrderId;
import com.example.domain.valueobject.RestaurantId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class RestaurantApprovalServiceImpl implements RestaurantApprovalService {

    private final RestaurantRepository restaurantRepo;
    private final RestaurantApprovalRepository approvalRepo;

    public RestaurantApprovalServiceImpl(RestaurantRepository restaurantRepo, RestaurantApprovalRepository approvalRepo) {
        this.restaurantRepo = restaurantRepo;
        this.approvalRepo = approvalRepo;
    }

    @Override
    public RestaurantApprovalResponse validateOrder(ValidateOrderCommand cmd) {

        Restaurant restaurant = restaurantRepo.findById(new RestaurantId(cmd.restaurantId()))
                .orElseThrow(() -> new IllegalStateException("Restaurant not found with ID: " + cmd.restaurantId()));

        Map<UUID, String> stockErrors = restaurantRepo.validateStockAvailability(
                new RestaurantId(cmd.restaurantId()),
                cmd.items()
        );

        boolean itemsOk = stockErrors.isEmpty();
        List<String> failureMessages = new ArrayList<>();

        if (!itemsOk) {
            stockErrors.forEach((productId, error) ->
                    failureMessages.add(String.format("Product %s: %s", productId, error))
            );
        }

        boolean stockReserved = false;
        if (itemsOk) {
            stockReserved = restaurantRepo.decreaseStockForItems(
                    new RestaurantId(cmd.restaurantId()),
                    cmd.items()
            );

            if (!stockReserved) {
                failureMessages.add("Failed to reserve stock - concurrent order conflict or insufficient stock");
            }
        }
        boolean finalApprovalStatus = itemsOk && stockReserved;

        RestaurantApproval approval = RestaurantApproval.validateAndCreate(
                restaurant,
                new OrderId(cmd.orderId()),
                cmd.totalPrice(),
                finalApprovalStatus
        );
        if (!failureMessages.isEmpty()) {
            approval.getFailureMessages().addAll(failureMessages);
        }
        approvalRepo.save(approval);
        return new RestaurantApprovalResponse(
                cmd.orderId(),
                approval.getStatus(),
                approval.getFailureMessages()
        );
    }
}
