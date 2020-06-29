package com.wynk.restaurant.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryPerson {

    private Long id;

    private DeliveryPersonStatus deliveryPersonStatus;

    public DeliveryPerson(Long id) {
        this.id = id;
        deliveryPersonStatus = DeliveryPersonStatus.IDLE;
    }

}
