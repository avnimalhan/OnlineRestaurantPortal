package com.wynk.restaurant.service;

import com.wynk.restaurant.controller.request.OrderRequest;
import com.wynk.restaurant.controller.request.OrderUpdationRequest;
import com.wynk.restaurant.controller.response.DeliveryPersonStatusResponse;
import com.wynk.restaurant.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private Restaurant restaurant = Restaurant.getInstance();


    public Order placeOrder(OrderRequest orderReq) {
        OrderStatus orderStatus = restaurant.isDeliveryPersonAvailable() ? OrderStatus.PICKED : OrderStatus.PENDING;
        Order order = new Order(orderReq.getItemId(), Long.valueOf(orderReq.getNoOfItems()), Long.valueOf(0),
                orderStatus);

        /*fetch an idle delivery person*/
        Optional<DeliveryPerson> optionalIdlePerson = restaurant.getDeliveryPerson().stream().
                filter(deliveryPerson -> DeliveryPersonStatus.IDLE.equals(deliveryPerson.getDeliveryPersonStatus()))
                .findFirst();
        if(optionalIdlePerson.isPresent()) {
            /*update order in process map*/
            DeliveryPerson idleDeliveryPerson = optionalIdlePerson.get();
            idleDeliveryPerson.setDeliveryPersonStatus(DeliveryPersonStatus.BUSY);
            restaurant.getOrderInProcess().put(order, idleDeliveryPerson);
        }
        else {
            /*update pending orders list*/
            restaurant.getPendingOrders().add(order);
        }
        return order;
    }

    public OrderStatus fetchOrderStatus(Long orderId) {

        return restaurant.getOrders().stream().filter(order -> orderId.equals(order.getId())).map(Order::getOrderStatus).findFirst().orElse(null);

    }

    public String updateDelivery(OrderUpdationRequest orderUpdate) {
        if(orderUpdate.getOrderStatus().equals(OrderStatus.DELIVERED)) {
            String orderId = orderUpdate.getOrderId();
            Order order = updateStatusInOrderList(orderId);

            /*update map*/
            order.setOrderStatus(OrderStatus.PENDING);
            restaurant.getOrderInProcess().remove(order);

            /*update delivery person status*/
            updateDeliverPersonStatus(restaurant.getOrderInProcess().get(order));
        }
        return orderUpdate.getOrderStatus().toString();
    }

    private Order updateStatusInOrderList(String orderId) {
        /*update status in order list*/
        Optional<Order> optionalOrder = restaurant.getOrders().stream().filter(order -> orderId.equals(order.getId())).findFirst();
        if(optionalOrder.isPresent()) {
            optionalOrder.get().setOrderStatus(OrderStatus.DELIVERED);
        }
        return optionalOrder.get();
    }

    private void updateDeliverPersonStatus(DeliveryPerson del) {
        int index = restaurant.getDeliveryPerson().indexOf(del);
        del.setDeliveryPersonStatus(DeliveryPersonStatus.IDLE);
        restaurant.getDeliveryPerson().set(index, del);
    }

    public List<DeliveryPersonStatusResponse> getActiveDeliveryPersonStatus() {
        List<DeliveryPersonStatusResponse> response = new ArrayList<>();
        for(DeliveryPerson d : restaurant.getDeliveryPerson()) {
            if(!d.getDeliveryPersonStatus().equals(DeliveryPersonStatus.INACTIVE)) {
                response.add(new DeliveryPersonStatusResponse(d));
            }
        }
        return response;
    }

}
