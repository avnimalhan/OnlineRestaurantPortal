package com.wynk.restaurant.controller.request;

import com.wynk.restaurant.model.OrderStatus;
import lombok.Getter;

@Getter
public class OrderUpdationRequest {

    private String orderId;

    private OrderStatus orderStatus;
}
