package com.wynk.restaurant.controller;


import com.wynk.restaurant.controller.request.OrderRequest;
import com.wynk.restaurant.controller.request.OrderUpdationRequest;
import com.wynk.restaurant.controller.response.DeliveryPersonStatusResponse;
import com.wynk.restaurant.controller.response.OrderResponse;
import com.wynk.restaurant.model.DeliveryPerson;
import com.wynk.restaurant.model.Order;
import com.wynk.restaurant.model.OrderStatus;
import com.wynk.restaurant.service.RestaurantService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    JSONObject obj;

    public RestaurantController(final RestaurantService restaurantService) {

        this.restaurantService = restaurantService;
    }

    @PostMapping("/1/orders")
    public OrderResponse placeOrder(final OrderRequest orderRequest) {
        Order order = restaurantService.placeOrder(orderRequest);
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setOrderStatus(order.getOrderStatus());
        return response;
    }

    @GetMapping("/1/orders/{orderId}")
    public JSONObject getOrderDetail(@PathVariable("orderId") final Long orderId) {
        obj = new JSONObject();
        OrderStatus detail = restaurantService.fetchOrderStatus(orderId);
        if(detail== null) {
            obj.put("status", "order not found");
        }else {
            obj.put("status", detail);
        }

        return obj;
    }

    @PatchMapping("/1/orders")
    public JSONObject updateDeliveryStatus(@RequestBody final OrderUpdationRequest orderUpdationRequest) {
        /*used patch mapping as only a few fields were needed to be updated */
        obj = new JSONObject();
        obj.put("status", restaurantService.updateDelivery(orderUpdationRequest));
        return obj;
    }

    @GetMapping("/1/delivery-person")
    public List<DeliveryPersonStatusResponse> getActiveDeliveryPersonStatus() {
        return restaurantService.getActiveDeliveryPersonStatus();
    }

}
