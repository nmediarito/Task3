package com.example.orderservice.events;

import com.example.orderservice.entities.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(String message, String productName, int quantity){
        applicationEventPublisher.publishEvent(new OrderEvent(message, productName, quantity));
    }

}
