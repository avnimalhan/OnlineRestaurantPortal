package com.wynk.restaurant.controller.response;

import com.wynk.restaurant.model.OrderStatus;
import lombok.Data;

@Data
public class OrderResponse {

    private String orderId;

    private OrderStatus orderStatus;
}
