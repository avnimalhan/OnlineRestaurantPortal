package com.wynk.restaurant.controller.response;

import com.wynk.restaurant.model.DeliveryPerson;
import com.wynk.restaurant.model.DeliveryPersonStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryPersonStatusResponse {

    private Long id;

    private String deliveryPersonStatus;

    public DeliveryPersonStatusResponse(DeliveryPerson deliveryPerson) {
        this.id = deliveryPerson.getId();
        this.deliveryPersonStatus = deliveryPerson.getDeliveryPersonStatus().toString();
    }
}
