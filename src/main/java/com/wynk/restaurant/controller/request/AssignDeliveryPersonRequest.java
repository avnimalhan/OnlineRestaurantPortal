package com.wynk.restaurant.controller.request;

import lombok.Getter;

@Getter
public class AssignDeliveryPersonRequest {

    private String orderId;

    private String deliveryPersonId;

}
