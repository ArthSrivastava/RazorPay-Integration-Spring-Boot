package com.example.razorpayintegrationspringboot.controllers;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentIntegrationController {

    @Value("${rzp.key.id}")
    private String keyId;

    @Value("${rzp.key.secret}")
    private String keySecret;

    @PostMapping("/order")
    public String createOrder(
            @RequestBody Map<String, Object> data
    ) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
        JSONObject orderRequest = new JSONObject();

        int amount = Integer.parseInt(data.get("amount").toString());
        //Convert amount to paise (amount * 100)
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        Order order = razorpayClient.orders.create(orderRequest);

        //save order details to DB as per requirement
        return order.toString();
    }


}
