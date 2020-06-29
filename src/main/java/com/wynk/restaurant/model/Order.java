package com.wynk.restaurant.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order {

    private String id;

    private Long timeDuration;

    private OrderStatus orderStatus;

    private List<Item> items;

    public Order(String itemId, Long quantity, Long timeDuration, OrderStatus orderStatus) {
        Item item = new Item(itemId, quantity);
        this.id = UUID.randomUUID().toString();
        this.timeDuration = item.getPrepTime()+timeDuration;
        this.orderStatus = orderStatus;

    }
}
