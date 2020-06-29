package com.wynk.restaurant.model;

import com.wynk.restaurant.controller.request.OrderRequest;
import lombok.Getter;
import lombok.Setter;
import sun.security.util.PendingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Restaurant {

    private Long id;

    private String name;

    private List<DeliveryPerson> deliveryPerson;

    private List<Order> orders;


    HashMap<Order, DeliveryPerson> orderInProcess;

    List<Order> pendingOrders;

    private static Restaurant restaurant = null;

    private Restaurant() {
        id = Long.valueOf(1);
        name = "XYZRestaurant";
        deliveryPerson = new ArrayList<>();
        orders = new ArrayList<>();
        for(int i=0; i< 5; i++) {
            deliveryPerson.add(new DeliveryPerson(Long.valueOf(i)));
        }
        orderInProcess = new HashMap<>();
        pendingOrders = new ArrayList<>();
    }

    public static Restaurant getInstance() {
        if(restaurant == null) {
            restaurant = new Restaurant();
        }
        return restaurant;
    }


    public boolean isDeliveryPersonAvailable() {

        return orderInProcess.size() < deliveryPerson.size();
    }
}
