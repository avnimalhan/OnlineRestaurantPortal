package com.wynk.restaurant.controller;

import com.wynk.restaurant.controller.request.AssignDeliveryPersonRequest;
import com.wynk.restaurant.controller.response.DeliveryPersonStateResponse;
import com.wynk.restaurant.service.DeliveryPersonService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deliveryPerson")
public class DeliveryPersonController {
    private final DeliveryPersonService deliveryPersonService;

    JSONObject obj;

    public DeliveryPersonController(final DeliveryPersonService deliveryPersonService) {

        this.deliveryPersonService = deliveryPersonService;
    }

    @PostMapping("/1/orderForDeliveryPerson")
    public JSONObject placeOrder(final AssignDeliveryPersonRequest assignDeliveryPersonRequest) {
        JSONObject obj = new JSONObject();
        deliveryPersonService.placeOrderForDeliveryPerson(assignDeliveryPersonRequest);
        obj.put("status", "Accepted");
        return obj;
    }

    @GetMapping("/1/deliveryPersonStatus/{personId}")
    public DeliveryPersonStateResponse getPersonDetail(@PathVariable("personId") final Long personId) {
        return deliveryPersonService.deliveryPersonState(personId);
    }


}
