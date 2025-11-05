package com.example.messaging.mapper;

import com.example.application.dtos.command.OrderedItem;
import com.example.application.dtos.command.ValidateOrderCommand;
import com.example.messaging.event.OrderPaidEvent;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RestaurantMessagingMapper {

    public ValidateOrderCommand orderPaidEventToValidateOrderCommand(OrderPaidEvent event) {
        List<OrderedItem> items = event.items()
                .stream()
                .map(i -> new OrderedItem(i.productId(), i.quantity()))
                .toList();

        return new ValidateOrderCommand(
                event.restaurantId(),
                event.orderId(),
                event.totalAmount(),
                items
        );
    }
}

