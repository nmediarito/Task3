package com.example.orderservice.services;

import com.example.orderservice.entities.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderEventHandler {

    @Autowired
    private RestTemplate restTemplate;

    @EventListener
    void handleEvent(OrderEvent event){
        System.out.println("ORDER HANDLED");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        //API request to update the product stock in Product microservice
        ResponseEntity<Object> updateQuantity  = restTemplate.exchange("http://localhost:8080/products/" + event.getProductName() + "/" + event.getQuantity() , HttpMethod.PUT, request, Object.class);
    }

}
