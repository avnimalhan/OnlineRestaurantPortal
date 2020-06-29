package com.wynk.restaurant.service;

import com.wynk.restaurant.controller.request.AssignDeliveryPersonRequest;
import com.wynk.restaurant.controller.response.DeliveryPersonStateResponse;
import com.wynk.restaurant.model.DeliveryPerson;
import com.wynk.restaurant.model.DeliveryPersonStatus;
import com.wynk.restaurant.model.Order;
import com.wynk.restaurant.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class DeliveryPersonService {

    Restaurant restaurant = Restaurant.getInstance();

    public void placeOrderForDeliveryPerson(AssignDeliveryPersonRequest request) {
        DeliveryPerson person = restaurant.getDeliveryPerson().stream().filter(deliveryPerson ->request.getDeliveryPersonId()
                .equals(deliveryPerson.getId())).findFirst().orElse(null);

        Optional<Order> orderToBePicked = restaurant.getOrders().stream().filter(order -> request.getOrderId().equals(order.getId())).findFirst();

        /*update status of delivery person*/
        person.setDeliveryPersonStatus(DeliveryPersonStatus.BUSY);


        /*update order status*/
        int orderIndex = restaurant.getOrders().indexOf(orderToBePicked);
        restaurant.getOrders().set(orderIndex, orderToBePicked.get());

        /*update order in processing orders map*/
        restaurant.getOrderInProcess().put(orderToBePicked.get(), person);

    }

    public DeliveryPersonStateResponse deliveryPersonState(Long personId) {
        DeliveryPersonStateResponse personState = new DeliveryPersonStateResponse();
        Optional<DeliveryPerson> person = restaurant.getDeliveryPerson().stream().filter(deliveryPerson -> personId.equals(deliveryPerson.getId())).findFirst();

        if(person.get().getDeliveryPersonStatus().equals(DeliveryPersonStatus.BUSY)) {
            //fetch order id from hashmap
            for(Map.Entry<Order, DeliveryPerson> map : restaurant.getOrderInProcess().entrySet()) {
                Order o = map.getKey();
                DeliveryPerson d = map.getValue();
                if(d.getId().equals(person.get().getId())) {
                    personState.setOrderId(o.getId());
                    personState.setState("ACTIVE");
                }
            }
        }
        else {
            /*not serving any order presently*/
            if(person.get().getDeliveryPersonStatus().equals(DeliveryPersonStatus.IDLE)) {
                personState.setState(DeliveryPersonStatus.IDLE.toString());
            }
            else {
                /*person is inactive*/
                personState.setState(DeliveryPersonStatus.INACTIVE.toString());
            }
        }
        return personState;
    }
}
