package com.wynk.restaurant.model;

import lombok.Getter;

@Getter
public class Item {

    private String itemId;

    private Long quantity;

    private Long prepTime;

    public Item(String itemId, Long quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.prepTime = Long.valueOf(10);   //default preparation time
    }
}
